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
        console.log("try send message:" + msg)
        try {
          //this.clientRef.sendMessage("/api/subcribe", JSON.stringify({"content":msg, 
           // "from":this.state.curUser.username, "toRoomID":this.state.curRoomID, "fromUserID": this.state.curUser.id}));
           this.clientRef.sendMessage("/api/subcribe", JSON.stringify({"content":msg, 
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

    render() {
        return (
        <div className="messenger-container">
            <RoomList rooms={this.state.rooms} subcribeToRoom={this.subcribeToRoom}/>
            <CreateRoom/>
            <MessageList messages={this.state.messages}/>
            <MessageForm sendMessage={this.sendMessage}/>
            
            <SockJsClient url='http://localhost:8080/handler' topics={['/socket/messages']}
                headers={this.httpHeader} subscribeHeaders={this.httpHeader}
                onMessage={this.onMessage}
                ref={ (client) => { this.clientRef = client }} /> 
          </div>
        )
    }
}

export default withRouter(Messenger);
