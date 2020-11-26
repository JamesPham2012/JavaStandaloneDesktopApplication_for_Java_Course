const { hostname } = require('os');

var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
// app.list(3000, '0.0.0.0');
server.listen(3000,function(){
    console.log("Server is now running");
});

app.get('/',(req,res)=>{
    res.send(hostname()); // just accept / route.
})

function Player(x,y,id,state){
    this.x=x;
    this.y=y;
    this.id = id;
    this.state = state;
}

function Coordinate(x,y){
    this.x=x;
    this.y=y;
}

var player = [];
var ID = [];
var length = 2;
var id2 = [];
var random_field1 = [];
var random_field2 = [];

io.on('connection',function(socket){
    console.log("Player Connected: "+socket.id);
    ID.push(socket.id);
    socket.emit('socketId',{id:socket.id});
    socket.on('Ready',function(data){
        if (id2.length==0){
            id2.push(socket.id);
            player.push(new Player(50,50,socket.id,true));
        }
        else{
            id2.push(socket.id);
            player.push(new Player(50,50,socket.id,true));
            for(let i=0;i<id2.length-1;i++){
                if(socket.id==id2[i]){
                   id2.pop();
                   player.pop();
                   break;
                }
            }
        }

        if(id2.length==length){
            io.emit("Start");
        }
    })
    socket.on('sendRandomBound',function(data){
        console.log("test random bound has runned");
        console.log(random_field1.length);
        if(random_field1.length==0){
            random_field1.push(data.id);
        }
        else{
            if(random_field1[0]!=data.id){
                random_field1.push(data.id);
            }
        }
        if(random_field1.length==2){
            var random = Math.floor(Math.random() * (data.upper-data.lower))+data.lower;
            random_field1.splice(0,2);
            console.log("Upper bound is: "+data.upper+" random number is: "+random);
            io.emit('receiveRandomBound',{rand:random});
        }
    })

    socket.on('sendRandomBound1',function(data){
        if(random_field2.length==0){
            random_field2.push(data.id);
        }
        else{
            if(random_field2[0]!=data.id){
                random_field2.push(data.id);
                var random = [];
                for(let i=0;i<data.size;i++){
                    var x =Math.floor(Math.random() * (data.upper-data.lower))+data.lower;
                    var y = Math.floor(Math.random() * 400)+160;
                    console.log(x+"_"+y);
                    random.push(new Coordinate(x,y));
                }                 
                random_field2.splice(0,2);
                io.emit('receiveRandomBound1',random);
                random.splice(0,data.size-1);
            }
        }
        
    })

    socket.on('EndGame',function(data){
        socket.broadcast.emit("winGame");
        id2.splice(0,2);
        ID.splice(0,2);
        player.splice(0,2);
    })
    
    socket.on('disconnect',function(){
        console.log("Player disconnected: "+socket.id);
        socket.broadcast.emit('playerDisconnected', { id: socket.id });
        		for(var i = 0; i < player.length; i++){
        			if(player[i].id == socket.id){
                        player.splice(i, 1);
                    }
                    if(ID[i]==socket.id){
                        ID.fill("",0,2);
                    }
                    if(id2[i]==socket.id){
                        id2.splice(i,1);
                    }
        		}
    })
})

