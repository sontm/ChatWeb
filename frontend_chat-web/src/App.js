import React, { Component } from 'react'

import logo from './logo.svg';
import './App.css';

import BackEnd from "./utils/BackEnd"

import SockJsClient from 'react-stomp';
import {BrowserRouter as Router} from "react-router-dom";
import {Route,withRouter,Switch} from 'react-router-dom';

import PrivateRoute from "./components/PrivateRoute"
import NotFound from "./components/NotFound"

import AppHeader from "./components/AppHeader"
import Home from "./components/Home"
import Login from "./user/login/Login"
import Messenger from "./components/messenger/Messenger"

import { Layout, notification } from 'antd';

const { Header, Footer, Sider, Content } = Layout;

class App extends Component {
  constructor() {
    super()
    this.state = {
      currentUser:"",
      isAuthenticated: false
    }

    this.loginDone = this.loginDone.bind(this)
    this.handleLogout = this.handleLogout.bind(this)
  }
  
  loginDone(data) {
    console.log(data);
    this.setState({
      isAuthenticated:true,
      currentUser:data.username
    });

    this.props.history.push("/");

    // BackEnd.getAllRooms(
    //   (data) => {
    //     this.setState({
    //       rooms: data
    //     })
    //   },
    //   (err) => {console.log("getAllRooms Error1:" + err)}
    // );
  }
  handleLogout(redirectTo="/", notificationType="success", description="You're successfully logged out.") {
    localStorage.removeItem("accessToken");

    this.setState({
      currentUser: "",
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);
    
    notification[notificationType]({
      message: 'Utility App',
      description: description,
    });
  }

  render () {  
    return (
      <Layout className="app-container">
        <AppHeader isAuthenticated={this.state.isAuthenticated} 
          currentUser={this.state.currentUser} 
          onLogout={this.handleLogout} />
        <Content className="app-content">
          <div className="container">
            <Switch>      
              <Route exact path="/" 
                render={(props) => <Home />}>
              </Route>
              <Route path="/login" 
                render={(props) => <Login onLogin={this.loginDone} {...this.state} />}></Route>

              <PrivateRoute authenticated={this.state.isAuthenticated} path="/messenger" 
                component={Messenger} {...this.state}></PrivateRoute>
              
              <Route component={NotFound}></Route>
            </Switch>
          </div>
        </Content>
    </Layout>
    );
  }
}

export default withRouter(App);
