import React, { Component } from 'react'
import CreateRoom from "./components/CreateRoom"
import MessageForm from "./components/MessageForm"
import MessageList from "./components/MessageList"
import RoomList from "./components/RoomList"
import BackEnd from "./utils/BackEnd"

import SockJsClient from 'react-stomp';
import Websocket from 'react-websocket';

import logo from './logo.svg';
import './App.css';

export default class App extends Component {
  constructor() {
    super()
    this.state = {
      rooms:[],
      messages:[],
      curMessage:"",
      curRoomID:-1,
      curUser:{username:"sontm", id:1}
    }

    this.onMessage = this.onMessage.bind(this)
    this.subcribeToRoom = this.subcribeToRoom.bind(this)
  }
  sendMessage = (msg) => {
    console.log("try send message:" + msg)
    try {
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

  subcribeToRoom(id) {
    console.log("try subcribe to room:" + id)
    this.setState({
      messages: [],
      curRoomID:id
    })
    if (this.clientRef) {
      this.clientRef.sendMessage("/api/subcribeToRoom", JSON.stringify({id: id}));
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
    return (
      <div className="app">
        <RoomList rooms={this.state.rooms} subcribeToRoom={this.subcribeToRoom}/>
        <CreateRoom/>
        <MessageList messages={this.state.messages}/>
        <MessageForm sendMessage={this.sendMessage}/>
        
        <SockJsClient url='http://localhost:8080/handler' topics={['/socket/messages']}
          onMessage={this.onMessage}
          ref={ (client) => { this.clientRef = client }} />        
        {/* <Websocket url='ws://localhost:8080/handler/socket/messages'
              onMessage={(msg) => { console.log("AAAAAA:" + msg); }} /> */}
      </div>
    );
  }
}

