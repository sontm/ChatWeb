import React, { Component } from 'react'
import CreateRoom from "./components/CreateRoom"
import MessageForm from "./components/MessageForm"
import MessageList from "./components/MessageList"
import RoomList from "./components/RoomList"
import Login from "./components/Login"

import BackEnd from "./utils/BackEnd"

import SockJsClient from 'react-stomp';
import Websocket from 'react-websocket';

import logo from './logo.svg';
import './App.css';

function createHeader() {
  if(localStorage.getItem("accessToken")) {
      var headers = {
                      'Content-Type': 'application/json',
                      'Authorization': 'Bearer ' + localStorage.getItem("accessToken")
                  };
  } else {
      var headers = {'Content-Type': 'application/json'};
  }
  return headers;
}

export default class App extends Component {
  constructor() {
    super()
    this.state = {
      rooms:[],
      messages:[],
      curMessage:"",
      curRoomID:-1,
      curUser:{username:"sontm", id:1},
      isLogined: false
    }

    this.onMessage = this.onMessage.bind(this)
    this.subcribeToRoom = this.subcribeToRoom.bind(this)
    this.doLogin = this.doLogin.bind(this)
  }
  sendMessage = (msg) => {
    console.log("try send message:" + msg)
    try {
      //this.clientRef.sendMessage("/api/subcribe", JSON.stringify({"content":msg, 
       // "from":this.state.curUser.username, "toRoomID":this.state.curRoomID, "fromUserID": this.state.curUser.id}));
       this.clientRef.sendMessage("/api/subcribe", JSON.stringify({"content":msg, 
        "from":this.state.curUser.username, "toRoomID":this.state.curRoomID, "fromUserID": this.state.curUser.id}));
      return true;
    } catch(e) {
      return false;
    }
  }
  onMessage(msg) {
    console.log(msg);
    this.setState({
      messages: [...this.state.messages, ...msg]
    })
  }
  doLogin(username, pwd) {
    console.log("Login:" + username + "," + pwd);
    var account = {
      username: username,
      password: pwd
    };

    BackEnd.login(account, 
      (data) => {
        console.log(data)
        this.setState({
          isLogined:true
        })
        localStorage.setItem("accessToken", data.accessToken);
        BackEnd.getAllRooms(
          (data) => {
            this.setState({
              rooms: data
            })
          },
          (err) => {console.log("getAllRooms Error1:" + err)}
        );
      },
      (err) => {console.log("getAllRooms Error1:" + err)});
  }

  subcribeToRoom(id) {
    console.log("try subcribe to room:" + id)
    this.setState({
      messages: [],
      curRoomID:id
    })
    if (this.clientRef) {
      let headers = createHeader();
      this.clientRef.sendMessage("/api/subcribeToRoom", JSON.stringify({id: id}, headers));
    }
    // BackEnd.getAllMessagesOfRoom( id, 
    //   (data) => {
    //     this.setState({
    //       messages: data
    //     })
    //   },
    //   (err) => {console.log("getAllMessages Error1:" + err)}
    // );
  }

  componentDidMount() {
    BackEnd.getAllRooms(
      (data) => {
        this.setState({
          rooms: data
        })
      },
      (err) => {console.log("getAllRooms Error1:" + err)}
    );
    //this.subcribeToRoom(1);
  }

  render () {
    console.log("App render:--->")
    console.log(this.state)
    if (this.state.isLogined) {
    //  if (true) {
      //subscribeHeaders={headers}
      let headers = createHeader();
      var SocketChat = <SockJsClient url='http://localhost:8080/handler' topics={['/socket/messages']}
        headers={headers} subscribeHeaders={headers}
        onMessage={this.onMessage}
        ref={ (client) => { this.clientRef = client }} />   ;
    } else {
      var SocketChat = "";
    }
    return (
      <div className="app">
        <Login doLogin={this.doLogin}/>
        <RoomList rooms={this.state.rooms} subcribeToRoom={this.subcribeToRoom}/>
        <CreateRoom/>
        <MessageList messages={this.state.messages}/>
        <MessageForm sendMessage={this.sendMessage}/>
        
        {SocketChat}
        {/* <Websocket url='ws://localhost:8080/handler/socket/messages'
              onMessage={(msg) => { console.log("AAAAAA:" + msg); }} /> */}
      </div>
    );
  }
}

