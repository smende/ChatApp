import React from "react";
import httpClient from "react-http-client";
import "./NewConversation.css";
class NewConversation extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            isDisabled : true
        };

        this.onEmailIdChange = this.onEmailIdChange.bind(this);
        this.onFormSubmit = this.onFormSubmit.bind(this);
    }

    render() {
        return (
                <div className="cards3 container card-body">
                    <form onSubmit={this.onFormSubmit}>
                             <div className="input-group">
                                <input type="text" className="form-control userEmail" placeholder="Enter email id. Include '@gmail.com' " id="userEmail" 
                                    onChange={this.onEmailIdChange}
                                />
                                <button type="submit" className={"btn btn-primary btn-sm "+(this.state.isDisabled ? 'disabled' : '')}>Start new conversation</button>
                            </div>

                    </form>
                </div>
        );
    }

    onEmailIdChange(event){

        let value = event.target.value;

        if(value === null || value.trim().length === 0)
        {
            this.setState({isDisabled : true});
            return;
        }else{
            this.setState({isDisabled : false});
        }
    }

    onFormSubmit(event){
        event.preventDefault();

        let userEmailNode = document.getElementById("userEmail");
        let userEmail = userEmailNode.value;

        if(this.state.isDisabled || userEmail === null || userEmail.trim().length<=0)
            return;

        httpClient.post("/api/conversations/own-with-other-username/"+userEmail).then(conversation =>{
            console.log(conversation);
            userEmailNode.value = "";
            this.setState({isDisabled : true});
        }).catch(err =>{
            console.log(err);
        })

    }

}

export default NewConversation;