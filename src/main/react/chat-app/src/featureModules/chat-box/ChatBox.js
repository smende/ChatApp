import React from "react";
import "./ChatBox.css";
import httpClient from "react-http-client";

class ChatBox extends React.Component{

    constructor(props){
        super(props);
        this.state = {
            conversations : [],
            selected : null,
            selectedConversationsMessages : [],
            messagesByConvId : {},
            newMsg : ""
         }

         this.addNewMessage = this.addNewMessage.bind(this);
    }

    componentDidMount(){
        httpClient.get("/api/conversations/own").then(convs =>{
            this.setState({conversations : convs});
            if(convs != null && convs.length > 0  ){
                this.setState({selected:convs[0]})
                this.loadMessagesBySelectedConversation(this.state.selected.id);
            }
            else
                this.setState({selected:null})

        }).catch(err =>{
            this.setState({conversations : [], selected:null});
        });
        
    }

    render(){
        return(
            <div className="chatBox container">
                <div className="conversationsList">
                    <div className="card">
                        <div className="card-header">
                                <h4>Conversations</h4>
                        </div>
                        <div className="convsBody">
                            <div className="convsDiv">
                                <ul className="list-group">
                                    {
                                        this.state.conversations.map(a => {
                                            return <button onClick={b => this.selectConversation(a)} className={"list-group-item leftAlign "+(this.state.selected && this.state.selected.id == a.id ? "active" : '')}  key={a.id}>{a.others[0].user.fullName}</button>
                                        })
                                    }
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="messages">
                    <div className="card">
                        <div className="card-header">
                            <h4>{this.state.selected && this.state.selected.others[0].user.fullName}</h4>
                        </div>
                        <div className="card-body msgsBody">
                                <div className="msgsDiv">
                                    {
                                        this.state.selectedConversationsMessages.map(msg => {
                                            return (
                                            <div className={"msgBlock "+(this.props.user.userName == msg.fromUser.userName ? 'ownMsg': '')} key={msg.id}>
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
                                                <input className={"btn btn-primary "+(this.state.newMsg.trim().length ==0 ? 'disabled' : '')} type="submit" value="Send" />
                                        </div>
                                    </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        );
    }

    selectConversation(conversation){
        this.setState({selected : conversation});
        this.loadMessagesBySelectedConversation(conversation.id);
    }
    
    onNewMsgValueChange(event){
        let newMsg = event.target.value;
        this.setState({newMsg:newMsg});
    }

    loadMessagesBySelectedConversation(conversationId){

        if(conversationId == undefined)
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

        if(msg == null || msg.trim().length == 0 || this.state.selected == null){
            return;
        }

        const msgToSend = {
            message : msg.trim(),
            conversation : {
                id: this.state.selected.id
            }
        };

        httpClient.post("/api/messages",msgToSend).then(resp =>{

            this.setState({newMsg : ""});
            document.getElementById("newMsg").value = "";

            this.loadMessagesBySelectedConversation(this.state.selected.id);
        }).catch(err =>{
            console.log(err);
        })

    }


}
export default ChatBox;