package game.demo;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import UI.MainClass;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

public class MyGdxGame implements Screen {

    //Player player; // null obj which not consist any method. --> it just save a variable __> different from c++.
    // In later we have to player = new Player() in somewhere--> but not remove new Player()
    Texture texture_plane = Assets.texture_plane;

    private Player player;
    private final float UPDATE_TIME=1/60;
    float timer;
    private String id;
    private HashMap<String, Player> anotherPlayer = new HashMap<String, Player>();
    public Vector<Bullet> bullet_arr = new Vector<>();
    private Enemy enemy = new Enemy();
    ScreenViewport viewport = new ScreenViewport();
    Stage stage;
    boolean pauseGame = false;
    private MainClass mainClass;
    private Socket socket;
    SpriteBatch batch = new SpriteBatch();


    public MyGdxGame(MainClass mainClass){
        player = new Player(texture_plane);
        this.mainClass = mainClass;
    }
    @Override
    public void show() {
        pauseGame=false;
        System.out.println("Show");

        // tuy ham show chay nhieu lan nhung socket chi co mot.

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
                // theo dòng từ trên xg.
                //updatetoServer(UPDATE_TIME);
            }}).on("getPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray data = (JSONArray) args[0];
                try {
                    for(int i=0;i<data.length();i++){
                        System.out.println(i+" : "+data.getJSONObject(i).getString("id"));

                        Player tempPlayer = new Player(texture_plane);

                        System.out.println("test");
                        tempPlayer.setX(((Double) data.getJSONObject(i).getDouble("x")).floatValue());
                        tempPlayer.setY(((Double) data.getJSONObject(i).getDouble("y")).floatValue());
                        tempPlayer.setExist(data.getJSONObject(i).getBoolean("exist"));

                        anotherPlayer.put(data.getJSONObject(i).getString("id"),tempPlayer);

                    }

                } catch (Exception e) {
                    Gdx.app.log("SocketIO", "Error getting Previous Players");
                }
                // theo dòng từ trên xg.
                //updatetoServer(UPDATE_TIME);
            }}).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    System.out.println("new player is: "+data.getString("id"));
                    Player tempPlayer = new Player(texture_plane);
                    anotherPlayer.put(data.getString("id"),tempPlayer);
                } catch (Exception e) {
                    Gdx.app.log("SocketIO", "Error getting new Players");
                }
                // theo dòng từ trên xg.
                //updatetoServer(UPDATE_TIME);
            }}).on("playerDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    anotherPlayer.remove(data.getString("id"));
                } catch (Exception e) {
                    Gdx.app.log("SocketIO", "Error delete Players");
                }
                // theo dòng từ trên xg.
                //updatetoServer(UPDATE_TIME);
            }})

                .on("sendAnotherPositionToClient", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONObject data = (JSONObject) args[0];
                        try{
                            String playerId = data.getString("id");
                            Double x = data.getDouble("x");
                            Double y = data.getDouble("y");
                            if((anotherPlayer.get(playerId)!=null)){
                                //System.out.println("test");

                                anotherPlayer.get(playerId).setX(x.floatValue());
                                anotherPlayer.get(playerId).setY(y.floatValue());
                                System.out.println(anotherPlayer.get(playerId).getX());
                                anotherPlayer.get(playerId).setExist(data.getBoolean("exist"));
                            }
                        }catch(Exception e){
                            System.out.println("error send postion of another players to client: "+e);
                        }
                    }
                });
        // Nhưng để ngoài thì asynchronous.





    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 10, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!pauseGame){
            batch.begin();
            updatetoServer(Gdx.graphics.getDeltaTime());


            player.render_player(batch);
            for(HashMap.Entry<String, Player> entry : anotherPlayer.entrySet()){
                entry.getValue().updateDraw();
                entry.getValue().render_player(batch);
            }
            batch.end();

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            mainClass.setMenuScreen();
        }


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

    public void updatetoServer(float dt){
        this.timer += dt;
        if(player.update()){
            JSONObject data = new JSONObject();
            try{
                data.put("x",player.getX());
                data.put("y",player.getY());
                data.put("id",id);
                data.put("exist",player.getExist());

                socket.emit("sendtoServer",data);
            }catch(Exception e){
                System.out.println("error update");
            }

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

        enemy.dispose();
        System.out.println("out");
    }
}


