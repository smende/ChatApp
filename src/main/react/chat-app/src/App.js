import React from 'react';
import './App.css';
import ChatBox from './featureModules/chat-box/ChatBox';
import TopNavBar from './featureModules/top-nav-bar/TopNavBar';
import httpClient from "react-http-client";

class App extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      isLoading : false,
      user : null
    };
  }

  componentDidMount(){
    this.setState({isLoading : true});

    httpClient.get("/api/user/current").then(user =>{
        this.setState({isLoading : false, user:user});
    }).catch(err =>{
        this.setState({isLoading : false, user:null});
    })    
}

render(){
  return(
    !this.state.isLoading && this.state.user != null  ? (
      <div className="App">
        <TopNavBar user={this.state.user}/>
        <ChatBox  user={this.state.user}/>
      </div>
    ): <div className="card card-body container"> User is not loaded!</div>
  );
}

}

export default App;
