import React, { Component } from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';
import './AppHeader.css';
import pollIcon from '../poll.svg';
import { Layout, Menu, Dropdown, Icon } from 'antd';
const Header = Layout.Header;
const { SubMenu } = Menu;

class AppHeader extends Component {
    constructor(props) {
        super(props);   
        this.handleMenuClick = this.handleMenuClick.bind(this);   
    }

    handleMenuClick({ key }) {
      console.log("handleMenuClick:" + key)
      if(key === "logout") {
        this.props.onLogout();
      }
    }

    render() {
        let menuItems;
        if(this.props.currentUser) {
          menuItems = [
            <Menu.Item key="/">
              <Link to="/">
                <Icon type="home" className="nav-icon" style={{fontSize: '20px'}}/>
              </Link>
            </Menu.Item>,
            <Menu.Item key="/poll/new">
            <Link to="/poll/new">
              <img src={pollIcon} alt="poll" className="poll-icon" style={{fontSize: '20px'}}/>
            </Link>
          </Menu.Item>,
          <SubMenu key="/profile" className="profile-menu" title={
              <span className="submenu-title-wrapper">
                <Icon type="user" className="nav-icon" style={{marginRight: 5, fontSize: '20px'}} /> <Icon type="down" />
              </span>
            } onClick={this.handleMenuClick}>
                <Menu.Item key="user-info" className="dropdown-item" disabled>
                  <div className="user-full-name-info">
                    @{this.props.currentUser}
                  </div>
                </Menu.Item>
                <Menu.Divider />
                <Menu.Item key="profile" className="dropdown-item">
                  <Link to={`/users/${this.props.currentUser}`}>
                    <Icon type="profile" style={{ fontSize: '18px'}}/>   Profile</Link>
                </Menu.Item>
                <Menu.Item key="logout" className="dropdown-item">
                  <Icon type="logout" style={{ fontSize: '18px'}}/>   Logout
                </Menu.Item>
          </SubMenu>
          ]; 
        } else {
          menuItems = [
            <Menu.Item key="/login">
              <Link to="/login"><Icon type="login" style={{ fontSize: '18px', color: '#08c' }}/>   Login</Link>
            </Menu.Item>,
            <Menu.Item key="/signup">
              <Link to="/signup"><Icon type="user-add" style={{ fontSize: '18px', color: '#08c' }}/>   Signup</Link>
            </Menu.Item>                  
          ];
        }

        return (
            <Header className="app-header">
            <div className="container">
              <div className="app-title" >
                <Link to="/">Utility App</Link>
              </div>
              <Menu
                className="app-menu"
                theme="light"
                mode="horizontal"
                selectedKeys={[this.props.location.pathname]}
                style={{ lineHeight: '64px' }} >
                  {menuItems}
              </Menu>
            </div>
          </Header>
        );
    }
}

export default withRouter(AppHeader);