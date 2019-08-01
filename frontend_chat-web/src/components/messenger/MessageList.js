import React, { Component } from 'react'

export default class MessageList extends Component {
    render() {
        if (!this.props.messages) {
            return (<div></div>)
        } else {
        return (
            <div className="message-list">
                {   
                        this.props.messages.map( msg => {
                            return (
                                <div key={msg.id} className="message">
                                    <div className="message-user" style={{fontWeight:"bold", margin:"5px"}}>
                                        {msg.from}
                                    </div>
                                    <div className="message-content" style={{border:"1px solid cyan", paddingLeft:"5px"}}>
                                        {msg.content}
                                    </div>
                                </div>
                            )
                        })
                }
            </div>
        )
        }
    }
}
