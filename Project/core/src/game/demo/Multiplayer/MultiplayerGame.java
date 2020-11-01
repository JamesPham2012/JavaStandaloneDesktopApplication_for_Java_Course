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
import org.json.JSONObject;

import java.util.Vector;

public class MultiplayerGame implements Screen {

    //Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
    // In later we have to player = new Player() in somewhere--> but not remove new Player()
    Texture texture_plane = Assets.texture_plane;

    public static Player_Multiplayer player;
    private Player_Multiplayer coopPlayer;

    private final float UPDATE_TIME=1/60;
    float timer;
    private static String id;
    static int rand;

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
                        coopPlayer = new Player_Multiplayer();
                        coopPlayer.setTexture(Assets.texture_plane2);
                        coopPlayer.setControllable(false);
                        player.create();
                        coopPlayer.create();

                    } catch (Exception e) {

                    }
                }})
                .on("sendAnotherPositionToClient", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONObject data = (JSONObject) args[0];
                        try{
                            coopPlayer.setX(data.getInt("x"));
                            coopPlayer.setY(data.getInt("y"));
                            coopPlayer.setExecute(data.getBoolean("execute"));
                            System.out.println("executed of coopPlayer: "+data.getBoolean("execute"));
                        }catch(Exception e){
                            System.out.println("error send postion of another players to client: "+e);
                        }
                    }
                }).on("sendAnotherTrigger", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONObject data = (JSONObject) args[0];
                        try{
                            coopPlayer.setFire(data.getBoolean("fire"));
                            if(data.getBoolean("fire")==true){
                                coopPlayer.Bullet_Call(bullet_arr);
                            }
                        }catch(Exception e){
                            System.out.println("error send trigger of another players to client: "+e);
                        }
                    }
                });
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

            Waves_Multiplayer.Wave_Come(enemy_arr);
            Enemy_Multiplayer.render(enemy_arr,batch);
            Enemy_Multiplayer.fire(enemy_arr,bullet_arr);
            Bullet_Multiplayer.render(bullet_arr,batch);
            player.checkCollision(bullet_arr);
            updatetoServer();
            if(player.State){
                player.render_player(batch);
            }
            else{
                System.out.println("send");
                JSONObject data = new JSONObject();
                try{
                    data.put("x",0);
                    data.put("y",0);
                    data.put("execute",true);
                    socket.emit("sendtoServer",data);
                }catch(Exception e){

                }
            }
            if(coopPlayer.State){
                coopPlayer.render_player(batch);
            }



            Enemy_Multiplayer.checkCollision(enemy_arr,bullet_arr);

            if(player.fire()){
                player.Bullet_Call(bullet_arr);
                triggerBullet(true);
            }
            else{
                triggerBullet(false);
            }
            System.out.println(coopPlayer.State+" "+player.State);
            if((coopPlayer.State==false)&& (player.State==false)){
                System.out.println("endGame");
                mainClass.setGameOverScreen();
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

    public void updatetoServer(){
        if(player.input()||!player.State){
            JSONObject data = new JSONObject();
            try{
                data.put("x",player.getX());
                data.put("y",player.getY());
                data.put("id",id);
                data.put("execute",!player.State);

                socket.emit("sendtoServer",data);
            }catch(Exception e){
                System.out.println("error update");
            }

        }
    }
    public void triggerBullet(boolean fire){
        JSONObject data = new JSONObject();
        try{
            data.put("fire",fire);
            socket.emit("sendTrigger",data);

        }catch(Exception e){
            System.out.println("error trigger");
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


