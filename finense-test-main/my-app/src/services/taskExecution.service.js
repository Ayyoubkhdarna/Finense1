import http from "../http-common";

class TaskExecutionDataService {
    getAll(params) {
        return http.get("/tasksexecutions", { params });
    }

    get(id) {
        return http.get(`/tasksexecutions/${id}`);
    }

    create(data) {
        return http.post("/tasksexecutions", data);
    }

    update(id, data) {
        return http.put(`/tasksexecutions/${id}`, data);
    }

    delete(id) {
        return http.delete(`/tasksexecutions/${id}`);
    }

    deleteAll() {
        return http.delete("/tasksexecutions");
    }
}

export default new TaskExecutionDataService();
