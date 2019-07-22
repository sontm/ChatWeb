import React, { Component } from 'react'

export default class MessageForm extends Component {
    constructor() {
        super()
        this.state = {
          message:"",
        }
    
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleKeyPress = this.handleKeyPress.bind(this)
    }
    handleChange(e) {
        this.setState ({
            message: e.target.value
        })
    }
    handleSubmit(e) {
        //e.preventDefault()
        this.props.sendMessage(this.state.message)
        this.setState ({
            message: ""
        })
    }
    handleKeyPress(e) {
        if(e.which == 13 && !e.shiftKey) {        
            this.handleSubmit()
            e.preventDefault();
            return false;
        }
    }

    render() {
        return (
            <div className="new-message">
            <form className="" style={{width:"100%"}}>
                <textarea onKeyPress={this.handleKeyPress}
                    rows="5" onChange={this.handleChange} 
                    value={this.state.message}
                    placeholder="Type and hit ENTER!"
                    type="text" style={{width:"100%", resize:"none"}}
                />
            </form>
            </div>
        )
    }
}
