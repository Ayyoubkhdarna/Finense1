import React, { Component } from "react";
import TaskExecutionDataService from "../services/taskExecution.service";
import { Link } from "react-router-dom";
import Pagination from "@material-ui/lab/Pagination";

export default class TaskExecutionsList extends Component {
    constructor(props) {
        super(props);
        this.onChangeSearchTaskConfigName = this.onChangeSearchTaskConfigName.bind(this);
        this.retrieveTaskExecutions = this.retrieveTaskExecutions.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.setActiveTaskExecution = this.setActiveTaskExecution.bind(this);
        this.removeAllTaskExecutions = this.removeAllTaskExecutions.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
        this.handlePageSizeChange = this.handlePageSizeChange.bind(this);

        this.state = {
            TaskExecutions: [],
            currentTaskExecution: null,
            currentIndex: -1,
            searchTaskConfigName: "",

            page: 1,
            count: 0,
            pageSize: 1,
        };

        this.pageSizes = [1, 6, 9];
    }

    componentDidMount() {
        this.retrieveTaskExecutions();
    }

    onChangeSearchTaskConfigName(e) {
        const searchTaskConfigName = e.target.value;

        this.setState({
            searchTaskConfigName: searchTaskConfigName,
        });
    }

    getRequestParams(searchTaskConfigName, page, pageSize) {
        let params = {};

        if (searchTaskConfigName) {
            params["TaskConfigName"] = searchTaskConfigName;
        }

        if (page) {
            params["page"] = page - 1;
        }

        if (pageSize) {
            params["size"] = pageSize;
        }

        return params;
    }

    retrieveTaskExecutions() {
        const { searchTaskConfigName, page, pageSize } = this.state;
        const params = this.getRequestParams(searchTaskConfigName, page, pageSize);

        TaskExecutionDataService.getAll(params)
            .then((response) => {
                const { TaskExecutions, totalPages } = response.data;

                this.setState({
                    TaskExecutions: TaskExecutions,
                    count: totalPages,
                });
                console.log(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    }

    refreshList() {
        this.retrieveTaskExecutions();
        this.setState({
            currentTaskExecution: null,
            currentIndex: -1,
        });
    }

    setActiveTaskExecution(taskExecution, index) {
        this.setState({
            currentTaskExecution: taskExecution,
            currentIndex: index,
        });
    }

    removeAllTaskExecutions() {
        TaskExecutionDataService.deleteAll()
            .then((response) => {
                console.log(response.data);
                this.refreshList();
            })
            .catch((e) => {
                console.log(e);
            });
    }

    handlePageChange(event, value) {
        this.setState(
            {
                page: value,
            },
            () => {
                this.retrieveTaskExecutions();
            }
        );
    }

    handlePageSizeChange(event) {
        this.setState(
            {
                pageSize: event.target.value,
                page: 1
            },
            () => {
                this.retrieveTaskExecutions();
            }
        );
    }

    render() {
        const {
            searchTaskConfigName,
            TaskExecutions,
            currentTaskExecution,
            currentIndex,
            page,
            count,
            pageSize,
        } = this.state;

        return (
            <div className="list row">
                <div className="col-md-8">
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search by TaskConfigName"
                            value={searchTaskConfigName}
                            onChange={this.onChangeSearchTaskConfigName}
                        />
                        <div className="input-group-append">
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={this.retrieveTaskExecutions}
                            >
                                Search
                            </button>
                        </div>
                    </div>
                </div>
                <div className="col-md-6">
                    <h4>TaskExecutions List</h4>

                    <div className="mt-3">
                        {"Items per Page: "}
                        <select onChange={this.handlePageSizeChange} value={pageSize}>
                            {this.pageSizes.map((size) => (
                                <option key={size} value={size}>
                                    {size}
                                </option>
                            ))}
                        </select>

                        <Pagination
                            className="my-3"
                            count={count}
                            page={page}
                            siblingCount={1}
                            boundaryCount={1}
                            variant="outlined"
                            shape="rounded"
                            onChange={this.handlePageChange}
                        />
                    </div>

                    <ul className="list-group">
                        {TaskExecutions &&
                        TaskExecutions.map((taskExecution, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveTaskExecution(taskExecution, index)}
                                key={index}
                            >
                                {taskExecution.TaskConfigName}
                            </li>
                        ))}
                    </ul>

                    <button
                        className="m-3 btn btn-sm btn-danger"
                        onClick={this.removeAllTaskExecutions}
                    >
                        Remove All
                    </button>
                </div>
                <div className="col-md-6">
                    {currentTaskExecution ? (
                        <div>
                            <h4>TaskExecution</h4>
                            <div>
                                <label>
                                    <strong>TaskConfigName:</strong>
                                </label>{" "}
                                {currentTaskExecution.TaskConfigName}
                            </div>
                            <div>
                                <label>
                                    <strong>message:</strong>
                                </label>{" "}
                                {currentTaskExecution.message}
                            </div>
                            <div>
                                <label>
                                    <strong>Status:</strong>
                                </label>{" "}
                                {currentTaskExecution.emailErrorSent ? "emailErrorSent" : "Pending"}
                            </div>

                            <Link
                                to={"/tasksexecutions/" + currentTaskExecution.id}
                                className="badge badge-warning"
                            >
                                Edit
                            </Link>
                        </div>
                    ) : (
                        <div>
                            <br />
                            <p>Please click on a TaskExecution...</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}
