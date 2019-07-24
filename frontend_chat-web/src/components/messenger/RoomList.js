import React, { Component } from 'react'

export default class RoomList extends Component {
    render() {
        if (!this.props.rooms) {
            return (<div></div>)
        } else {
        return (
            <div className="room-list">
            <ul>
                {   
                        this.props.rooms.map( room => {
                            console.log(room)
                            return (
                                <li key={room.id}>
                                    <a href="#" onClick={() => this.props.subcribeToRoom(room.id)}># {room.roomname}</a>
                                </li>
                            )
                        })
                }
            </ul></div>
        )
        }
    }
}
