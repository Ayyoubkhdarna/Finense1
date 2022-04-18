import React, { Component } from "react";
import TaskExecutionDataService from "../services/taskExecution.service";

export default class AddTaskExecution extends Component {
    constructor(props) {
        super(props);
        this.onChangeTaskConfigName = this.onChangeTaskConfigName.bind(this);
        this.onChangeMessage = this.onChangeMessage.bind(this);
        this.saveTaskExecution = this.saveTaskExecution.bind(this);
        this.newTaskExecution = this.newTaskExecution.bind(this);

        this.state = {
            id: null,
            taskConfigName: "",
            message: "",
            emailErrorSent: false,

        };
    }

    onChangeTaskConfigName(e) {
        this.setState({
            taskConfigName: e.target.value
        });
    }

    onChangeMessage(e) {
        this.setState({
            message: e.target.value
        });
    }

    saveTaskExecution() {
        var data = {
            taskConfigName: this.state.taskConfigName,
            message: this.state.message
        };

        TaskExecutionDataService.create(data)
            .then(response => {
                this.setState({
                    id: 10,
                    taskConfigName: 'response.data.taskConfigName',
                    message: 'response.data.message',
                    emailErrorSent:true,

                });
                console.log(response.data.tasksExecutions);
            })
            .catch(e => {
                console.log(e);
            });
    }

    newTaskExecution() {
        this.setState({
            id: null,
            taskConfigName: "",
            message: "",
            emailErrorSent: false,

        });
    }

    render() {
        return (
            <div className="submit-form">
                {this.state.submitted ? (
                    <div>
                        <h4>You submitted successfully!</h4>
                        <button className="btn btn-success" onClick={this.newTaskExecution}>
                            Add
                        </button>
                    </div>
                ) : (
                    <div>
                        <div className="form-group">
                            <label htmlFor="taskConfigName">taskConfigName</label>
                            <input
                                type="text"
                                className="form-control"
                                id="taskConfigName"
                                required
                                value={this.state.taskConfigName}
                                onChange={this.onChangeTaskConfigName}
                                name="taskConfigName"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="message">Message</label>
                            <input
                                type="text"
                                className="form-control"
                                id="message"
                                required
                                value={this.state.message}
                                onChange={this.onChangeMessage}
                                name="message"
                            />
                        </div>

                        <button onClick={this.saveTaskExecution} className="btn btn-success">
                            Submit
                        </button>
                    </div>
                )}
            </div>
        );
    }
}
