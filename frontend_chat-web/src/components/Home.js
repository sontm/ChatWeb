import React, { Component } from 'react'
import {
    Link,
    withRouter
} from 'react-router-dom';
import { Icon } from 'antd';

export default class Home extends Component {
    render() {
        return (
            <div>
                <h1>Welcome to Home</h1>
                <br/>
                <Link to="/messenger">
                    Messenger
                </Link>
            </div>
        )
    }
}
