import React from  "react";

class ConversationList extends React.Component{

    constructor(props){
        super(props);

        this.selectConversationInChildComponent = this.selectConversationInChildComponent.bind(this);
    }

    render(){
        return(
                <div className="conversationsList">
                    <div className="card">
                        <div className="card-header">
                                <h4>Conversations</h4>
                        </div>
                        <div className="convsBody">
                            <div className="convsDiv">

                            <ul className="list-group">
                                    {
                                        this.props.conversations.map(a => {
                                            return <button onClick={b => this.selectConversationInChildComponent(a)} className={"list-group-item leftAlign "+(this.props.selectedConversationId && this.props.selectedConversationId === a.id ? "active" : '')}  key={a.id}>{a.others[0].user.userName}</button>
                                        })
                                    }
                            </ul>                                                
                            </div>
                        </div>
                    </div>
                </div>
        )
    }

    selectConversationInChildComponent(convesation){
            this.props.selectConversation(convesation);
    }

}

export default ConversationList;