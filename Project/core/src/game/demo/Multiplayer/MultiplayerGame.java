package game.demo.Multiplayer;


import UI.Multiplayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import UI.MainClass;
import game.demo.Assets;
import game.demo.Background;
import game.demo.Item;
import game.demo.Waves;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Vector;

public class MultiplayerGame implements Screen {

    //Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
    // In later we have to player = new Player() in somewhere--> but not remove new Player()
    Texture texture_plane = Assets.texture_plane;

    public static Player_Multiplayer player;
    float timer;
    public static String id;
    public static int point;
    static int rand=0; // for random waves.
    static Vector<Integer> randPosition1 ;
    static Vector<Integer> randPosition2;
    static boolean isAction;
    Label label_point;

    Background background;
    static int Wave;

    ScreenViewport viewport = new ScreenViewport();
    Stage stage;
    boolean pauseGame;
    private MainClass mainClass;
    public static Socket socket;
    SpriteBatch batch;
    Label guide;

    public MultiplayerGame(MainClass mainClass){
        this.mainClass = mainClass;

        label_point  = new Label("0",new Skin(Gdx.files.internal("skin/Textfield.json")));
        randPosition1 = new Vector();
        randPosition2 = new Vector();
        point = 0;
        isAction = false;
        Wave=0;
        background = new Background();
        pauseGame = true;
        guide = new Label("Press Enter to ready ",new Skin(Gdx.files.internal("skin/Textfield.json")));
        stage = new Stage();
        batch = new SpriteBatch();
        label_point.setText(point);
        label_point.setFontScale(2f);
        label_point.setPosition(0,Gdx.graphics.getHeight()-50);
        stage.addActor(label_point);
        stage.addActor(guide);
        background.create();
        background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());



    }

    @Override
    public void show() {
        System.out.println("Server IP: "+Multiplayer.ServerIP);
        connectSocket(); // copy file gradle build. thu muc server.

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");

            }
        });
        socket.on("socketId", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    id = data.getString("id");
                    Gdx.app.log("SocketIO", "My ID: " + id);

                } catch (Exception e) {
                    Gdx.app.log("SocketIO", "Error getting ID");
                }
            }})
            .on("Start", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        pauseGame = false;
                        player = new Player_Multiplayer();
                        player.create();
                    } catch (Exception e) {

                    }
                }})
                .on("winGame", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        try {
                            System.out.println("winGame");
                            Bullet_Multiplayer.bullet_arr.clear();
                            Enemy_Multiplayer.enemy_arr.clear();
                            Waves.reset();
                            Wave=0;
                            mainClass.setWinScreen();

                        } catch (Exception e) {

                        }
                    }});


        // Nhưng để ngoài thì asynchronous.
    }

    public static int RandomBound(int lower_bound,int upper_bound){
        JSONObject data = new JSONObject();
        try{
            data.put("id",id);
            data.put("lower",lower_bound);
            data.put("upper",upper_bound);
            socket.emit("sendRandomBound",data);
        }catch(Exception e){
            System.out.println(e+": Error random bound from "+lower_bound+"-"+upper_bound);
        }
        socket.on("receiveRandomBound", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    rand = data.getInt("rand");
                    System.out.println(rand);
                } catch (Exception e) {
                    Gdx.app.log("SocketIO", "Error getting rand");
                }
            }});
        return rand;
    }


    public static int RandomBound1(int lower_bound,int upper_bound,int size){
        JSONObject data = new JSONObject();
        try{
            data.put("id",id);
            data.put("size",size);
            data.put("lower",lower_bound);
            data.put("upper",upper_bound);
            socket.emit("sendRandomBound1",data);
        }catch(Exception e){
            System.out.println(e+": Error random bound from "+lower_bound+"-"+upper_bound);
        }
        socket.on("receiveRandomBound1", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray data = (JSONArray) args[0];
                try {
                    for(int i=0;i<data.length();i++){
                        randPosition1.addElement(data.getJSONObject(i).getInt("x"));
                        randPosition2.addElement(data.getJSONObject(i).getInt("y"));
                        System.out.println(randPosition1.lastElement()+"-"+randPosition2.lastElement());
                    }
                    isAction=true;

                } catch (Exception e) {
                    Gdx.app.log("SocketIO", "Error getting rand");
                }

            }});
        return size;
    }

    public void EndGame(){
        JSONObject data = new JSONObject();
        try{
            data.put("id",id);
            socket.emit("EndGame",data);
        }catch(Exception e){
            System.out.println("Cannot endGame");
        }
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 10, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.render();
        stage.act();
        stage.draw();

        if(pauseGame){
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                guide.setText("Ready!");
                JSONObject data = new JSONObject();
                try{
                    data.put("ready",id);
                    socket.emit("Ready",data);
                }catch(Exception e){
                }
            }
        }
       else{
//           System.out.println("start");
            Waves_Multiplayer.Wave_Come();
            Enemy_Multiplayer.render(batch);
            Enemy_Multiplayer.fire();
            Bullet_Multiplayer.render(batch);
            player.checkCollision();
            if(player.State){
                player.render_player(batch);
            }
            label_point.setText(point);
            Enemy_Multiplayer.checkCollision();
            Item_Multiplayer.render(batch);

            if(player.fire()){
                player.Bullet_Call();
            }
            if(!player.State){
                EndGame();
                System.out.println("loseGame");
                Bullet_Multiplayer.bullet_arr.clear();
                Enemy_Multiplayer.enemy_arr.clear();
                Item.item_arr.clear();
                mainClass.setLostScreen();
                Waves.reset();
                Wave=0;
            }
        }
        batch.end();
    }

    public void connectSocket(){
        try{
            socket = IO.socket("http://"+ Multiplayer.ServerIP+":3000");
            socket.connect();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        System.out.println("Pause");
        pauseGame=true; // pause is 1

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        pauseGame=true;

    }

    public void dispose(){
        System.out.println("out");
    }
}


