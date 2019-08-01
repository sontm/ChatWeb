import React, { Component } from 'react'

import './Messenger.css';

import CreateRoom from "./CreateRoom"
import MessageForm from "./MessageForm"
import MessageList from "./MessageList"
import RoomList from "./RoomList"

import BackEnd from "../../utils/BackEnd"

import SockJsClient from 'react-stomp';
import {BrowserRouter as Router} from "react-router-dom";
import {Route,withRouter,Switch} from 'react-router-dom';
//const BASE_NAME = "/utility";
const BASE_NAME = "";
class Messenger extends Component {
    constructor() {
        super()
        console.log ("Constructor Messenger ..........")
        this.state = {
          rooms:[],
          messages:[],
          curMessage:"",
          curRoomID:-1
        }
    
        this.onMessage = this.onMessage.bind(this)
        this.subcribeToRoom = this.subcribeToRoom.bind(this)
        this.sendMessage = this.sendMessage.bind(this)

        this.httpHeader = BackEnd.createHeader();
    }

    sendMessage = (msg) => {
        
        try {
           this.clientRef.sendMessage("/api/subcribe/newMessage", JSON.stringify({"content":msg,
            "from":this.props.currentUser, "toRoomID":this.state.curRoomID}));
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
        if (this.clientRef) {
          console.log("newJoinRoom:" + id)
            // THis should NOT have Context path here
          this.clientRef.sendMessage("/api/subcribe/newJoinRoom", JSON.stringify({"id":id}));
        }
        this.setState({
          messages: [],
          curRoomID:id
        })
    }

    componentDidMount() {
        BackEnd.getAllRooms({username:this.props.currentUser},
          (data) => {
            this.setState({
              rooms: data
            })
          },
          (err) => {console.log("getAllRooms Error1:" + err)}
        );
    }
    render() {
        var url = "http://localhost:8080" + BASE_NAME + "/socket";
        return (
        <div className="messenger-container">
            <RoomList rooms={this.state.rooms} subcribeToRoom={this.subcribeToRoom}/>
            <CreateRoom/>
            <MessageList messages={this.state.messages}/>
            <MessageForm sendMessage={this.sendMessage}/>
            
            <SockJsClient url={url} topics={['/user/queue/messages']}
              headers={this.httpHeader} subscribeHeaders={this.httpHeader}
              onMessage={this.onMessage}
              ref={ (client) => { this.clientRef = client }} />
          </div>
        )
    }
}

export default withRouter(Messenger);
