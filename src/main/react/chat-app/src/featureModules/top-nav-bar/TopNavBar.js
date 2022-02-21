import React from "react";
import httpClient from "react-http-client";
import "./TopNavBar.css"

class TopNavBar extends React.Component{

constructor(props){
    super(props);
}

render(){
    return(
        <div className="navDiv">
                    <nav className="navbar navbar-light bg-light">
                        <span className="navbar-brand">Chat App</span>
                        <span>{this.props.user && (this.props.user.firstName+" "+this.props.user.lastName)}</span>
                    </nav>
        </div>
    );
}

}
export default TopNavBar;