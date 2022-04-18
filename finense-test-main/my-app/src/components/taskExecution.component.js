import React, { Component } from "react";
import TaskExecutionDataService from "../services/taskExecution.service";

export default class TaskExecution extends Component {
    constructor(props) {
        super(props);
        this.onChangeTaskConfigName = this.onChangeTaskConfigName.bind(this);
        this.onChangeMessage = this.onChangeMessage.bind(this);
        this.getTaskExecution = this.getTaskExecution.bind(this);
        this.updateTaskExecution = this.updateTaskExecution.bind(this);
        this.deleteTaskExecution = this.deleteTaskExecution.bind(this);

        this.state = {
            currentTaskExecution: {
                id: null,
                taskConfigName: "",
                message: "",
                emailErrorSent: false
            },
            message: ""
        };
    }

    componentDidMount() {
        this.getTaskExecution(this.props.match.params.id);
    }

    onChangeTaskConfigName(e) {
        const taskConfigName = e.target.value;

        this.setState(function(prevState) {
            return {
                currentTaskExecution: {
                    ...prevState.currentTaskExecution,
                    taskConfigName: taskConfigName
                }
            };
        });
    }

    onChangeMessage(e) {
        const message = e.target.value;

        this.setState(prevState => ({
            currentTaskExecution: {
                ...prevState.currentTaskExecution,
                message: message
            }
        }));
    }

    getTaskExecution(id) {
        TaskExecutionDataService.get(id)
            .then(response => {
                this.setState({
                    currentTaskExecution: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    updatePublished(status) {
        var data = {
            id: this.state.currentTaskExecution.id,
            taskConfigName: this.state.currentTaskExecution.taskConfigName,
            message: this.state.currentTaskExecution.message,
            emailErrorSent: status
        };

        TaskExecutionDataService.update(this.state.currentTaskExecution.id, data)
            .then(response => {
                this.setState(prevState => ({
                    currentTaskExecution: {
                        ...prevState.currentTaskExecution,
                        emailErrorSent: status
                    }
                }));
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    updateTaskExecution() {
        TaskExecutionDataService.update(
            this.state.currentTaskExecution.id,
            this.state.currentTaskExecution
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The TaskExecution was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    deleteTaskExecution() {
        TaskExecutionDataService.delete(this.state.currentTaskExecution.id)
            .then(response => {
                console.log(response.data);
                this.props.history.push('/TaskExecutions')
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentTaskExecution } = this.state;

        return (
            <div>
                {currentTaskExecution ? (
                    <div className="edit-form">
                        <h4>TaskExecution</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="titaskConfigNametle">TaskConfigName</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="taskConfigName"
                                    value={currentTaskExecution.taskConfigName}
                                    onChange={this.onChangeTaskConfigName}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="message">message</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="message"
                                    value={currentTaskExecution.message}
                                    onChange={this.onChangeMessage}
                                />
                            </div>

                            <div className="form-group">
                                <label>
                                    <strong>Status:</strong>
                                </label>
                                {currentTaskExecution.emailErrorSent ? "emailErrorSent" : "Pending"}
                            </div>
                        </form>

                        {currentTaskExecution.emailErrorSent ? (
                            <button
                                className="badge badge-primary mr-2"
                                onClick={() => this.updatePublished(false)}
                            >
                                UnPublish
                            </button>
                        ) : (
                            <button
                                className="badge badge-primary mr-2"
                                onClick={() => this.updatePublished(true)}
                            >
                                Publish
                            </button>
                        )}

                        <button
                            className="badge badge-danger mr-2"
                            onClick={this.deleteTaskExecution}
                        >
                            Delete
                        </button>

                        <button
                            type="submit"
                            className="badge badge-success"
                            onClick={this.updateTaskExecution}
                        >
                            Update
                        </button>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a TaskExecution...</p>
                    </div>
                )}
            </div>
        );
    }
}
