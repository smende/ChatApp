import React from "react";
import httpClient from "react-http-client";

class Messages extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            selectedConversationsMessages : [],
            newMsg : ""
        }

        this.onNewMsgValueChange = this.onNewMsgValueChange.bind(this);
        this.addNewMessage = this.addNewMessage.bind(this);
        this.loadMessagesBySelectedConversation = this.loadMessagesBySelectedConversation.bind(this);
    }
    
    render(){
        return(
        <div className="messages">
            <div className="card">
                <div className="card-header">
                    <h4>{this.props.selected ? this.props.selected.others[0].user.userName : 'Select any conversation...'}</h4>
                </div>
                <div className="card-body msgsBody">
                        <div className="msgsDiv">
                            {
                                this.state.selectedConversationsMessages.map(msg => {
                                    return (
                                    <div className={"msgBlock "+(this.props.user.userName === msg.fromUser.userName ? 'ownMsg': '')} key={msg.id}>
                                        <div>
                                            {msg.message}
                                        </div>
                                        <label className="timeStamp">{msg.createdOn}</label>
                                    </div>);
                                })
                            }
                        </div>
                </div>
                <div className="card-footer ftr">
                    <form onSubmit={this.addNewMessage}>
                            <div className="ftrMain">
                                <div className="w100">
                                        <textarea className="newMsg" id="newMsg" onChange={this.onNewMsgValueChange.bind(this)} ></textarea>
                                </div>
                                <div>
                                        <input className={"btn btn-primary "+(this.state.newMsg.trim().length ===0 ? 'disabled' : '')} type="submit" value="Send" />
                                </div>
                            </div>
                    </form>
                </div>
            </div>

        </div>
        );
    }

    onNewMsgValueChange(event){
        let newMsg = event.target.value;
        this.setState({newMsg:newMsg});
    }

    loadMessagesBySelectedConversation(conversationId){

        if(conversationId === undefined)
            return;
        httpClient.get("/api/messages/conversation/"+conversationId).then(messages =>{
             this.setState({selectedConversationsMessages : messages});
        }).catch(err =>{
            this.setState({selectedConversationsMessages : [] });
        })
        
    }

    addNewMessage(event){
        event.preventDefault();

        let msg = this.state.newMsg;

        if(msg === null || msg.trim().length === 0 || this.state.selected === null){
            this.setState({newMsg:null});
            return;
        }

        const msgToSend = {
            message : msg.trim(),
            conversation : {
                id: this.props.selected.id
            }
        };

        httpClient.post("/api/messages",msgToSend).then(resp =>{

            this.setState({newMsg : ""});
            document.getElementById("newMsg").value = "";

            this.loadMessagesBySelectedConversation(this.props.selected.id);
        }).catch(err =>{
            console.log(err);
        })

    }
}

export default Messages;