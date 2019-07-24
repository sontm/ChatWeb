import React, { Component } from 'react'

export default class Login extends Component {
    constructor() {
        super()
        this.state = {
          username:"",
          password:""
        }
    
        this.handleUserNameChange = this.handleUserNameChange.bind(this)
        this.handlePasswordChange = this.handlePasswordChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleUserNameChange(e) {
        this.setState ({
            username: e.target.value
        })
    }
    handlePasswordChange(e) {
        this.setState ({
            password: e.target.value
        })
    }
    handleSubmit(e) {
        e.preventDefault()
        this.props.doLogin(this.state.username, this.state.password)
    }

    render() {
        return (
            <form className="login-form" onSubmit={this.handleSubmit}>
                <input  onChange={this.handleUserNameChange} 
                    value={this.state.username}
                    placeholder="User Name"
                    type="text"
                />
                <input  onChange={this.handlePasswordChange} 
                    value={this.state.password}
                    type="text"
                />
                <button type="submit">Login</button>
            </form>
        )
    }
}
