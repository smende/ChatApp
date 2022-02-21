import React from "react";
import "./TopNavBar.css"

class TopNavBar extends React.Component{

render(){
    return(
        <nav className="navbar navbar-light bg-light navDiv">
            <span className="navbar-brand">Chat App</span>
            <div>
            <span>{this.props.user && (this.props.user.userName)}</span>
            <img className="pic" src={this.props.user.picture} alt="DP"/>
            </div>
        </nav>
);
}

}
export default TopNavBar;