import React, { Fragment } from "react";
import "./ChatBox.css";
import httpClient from "react-http-client";
import NewConversation from "../new-conversation/NewConversation";

import ConversationList from "../conversation-list/ConversationList";
import Messages from "../messages/Messages";

class ChatBox extends React.Component{

    constructor(props){
        super(props);
        this.state = {
            conversations : [],
            selected : null
         }

         this.selectConversation = this.selectConversation.bind(this);
         this.messagesRef = React.createRef();
    }

    componentDidMount(){
        httpClient.get("/api/conversations/own").then(convs =>{
            this.setState({conversations : convs});
            if(convs != null && convs.length > 0  ){
                this.selectConversation(convs[0]);
            }
            else
                this.selectConversation(null);

        }).catch(err =>{
            this.setState({conversations : []});
            this.selectConversation(null);
        });
    }

    render(){
        return(
            <Fragment>
            <NewConversation/>
            <div className="chatBox container">
                
                <ConversationList conversations={this.state.conversations} 
                                  selectedConversationId={this.state.selected && this.state.selected.id}
                                  selectConversation={this.selectConversation}/>

                <Messages selected={this.state.selected} 
                          user={this.props.user} 
                          ref={this.messagesRef} />

            </div>
            </Fragment>
        );
    }

    selectConversation(conversation){
        this.setState({selected : conversation});
        this.messagesRef.current.loadMessagesBySelectedConversation(conversation != null ? conversation.id : null);
    }

}
export default ChatBox;