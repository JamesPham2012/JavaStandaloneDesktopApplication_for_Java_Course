var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(3000,function(){
    console.log("Server is now running");
});

function Player(x,y,id,exist){
    this.x=x;
    this.y=y;
    this.id = id;
    this.exist=exist;
}
var player = [];

io.on('connection',function(socket){

    console.log("Player Connected: "+socket.id);
    socket.emit('socketId',{id:socket.id});
    socket.emit('getPlayer',player);
    socket.broadcast.emit('newPlayer',{id:socket.id});
    socket.on('sendtoServer',function(data){
        socket.broadcast.emit("sendAnotherPositionToClient",data);
        for(var i=0;i<player.length;i++){
            if(player[i].id==data.id){
                player[i].x = data.x;
                player[i].y = data.y;
                player[i].exist = data.exist;
            }
        }
    })
    socket.on('disconnect',function(){
        console.log("Player disconnected: "+socket.id);
        socket.broadcast.emit('playerDisconnected', { id: socket.id });
        		for(var i = 0; i < player.length; i++){
        			if(player[i].id == socket.id){
        				player.splice(i, 1);
        			}
        		}

    })
    player.push(new Player(50,50,socket.id,true));
})

