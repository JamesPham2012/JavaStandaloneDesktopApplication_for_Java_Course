package game.demo.Multiplayer;


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
import game.demo.Waves;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class MultiplayerGame implements Screen {

    //Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
    // In later we have to player = new Player() in somewhere--> but not remove new Player()
    Texture texture_plane = Assets.texture_plane;

    public static Player_Multiplayer player;
    private final float UPDATE_TIME=1/60;
    float timer;
    public static String id;
    public static int point = 0;
    static int rand=0; // for random waves.
    static Vector<Integer> randPosition1 = new Vector();
    static Vector<Integer> randPosition2 = new Vector();
    static boolean isAction = false;
    static boolean ar=false;
    Label label_point = new Label("0",new Skin(Gdx.files.internal("skin/Textfield.json")));
    static String id_rand; // for random id to bullet aim enemy.

    public Vector<Bullet_Multiplayer> bullet_arr = new Vector<>();
    Vector<Enemy_Multiplayer> enemy_arr = new Vector<>();
    Background background = new Background();
    static int Wave=0;

    ScreenViewport viewport = new ScreenViewport();
    Stage stage;
    boolean pauseGame = true;
    private MainClass mainClass;
    public static Socket socket;
    SpriteBatch batch = new SpriteBatch();
    Label guide = new Label("Press Enter to ready ",new Skin(Gdx.files.internal("skin/Textfield.json")));

    public MultiplayerGame(MainClass mainClass){
        stage = new Stage();
        label_point.setText(point);
        label_point.setFontScale(2f);
        label_point.setPosition(0,Gdx.graphics.getHeight()-50);
        stage.addActor(label_point);
        stage.addActor(guide);
        background.create();
        background.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.mainClass = mainClass;


    }

    @Override
    public void show() {
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
                            mainClass.setMenuScreen();
                            Waves.reset();
                            Wave=0;
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
                mainClass.setMenuScreen();
                Waves.reset();
                Wave=0;
            }
        }
        batch.end();
    }

    public void connectSocket(){
        try{
            socket = IO.socket("http://localhost:3000");
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


