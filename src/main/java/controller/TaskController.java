/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import static com.sun.javafx.scene.CameraHelper.project;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;
import model.Task;
/**
 *
 * @author debora
 */
public class TaskController {
    public void save(Task task) {
        String sql = "INSERT INTO tasks(idProject"
                + "name, "
                + "description, " 
                + "completed, "
                + "notes, "
                + "deadline, "
                + "createdAt, "
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Cria uma conex�o com o banco
            connection = ConnectionFactory.getConnection();
            //Cria um PreparedStatment, classe usada para executar a query
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            //Executa a sql para inser��o dos dados
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void update(Task task) {

        String sql = "UPDATE tasks SET" 
                +"idproject = ?, "
                +"name = ?, "
                +"description = ?, "
                +"notes = ?, "
                +"completed = ?, "
                +"deadline = ?, "
                +"createdAt = ?, "
                +"updatedAt = ?, "
                +"WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Cria uma conex�o com o banco
            connection = ConnectionFactory.getConnection();
            //Cria um PreparedStatment, classe usada para executar a query
            statement = connection.prepareStatement(sql);

            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            //Executa a sql para inser��o dos dados
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa" + ex.getMessage(), ex);
        } finally {
           ConnectionFactory.closeConnection(connection, statement);
        }
    }
    public void removeById(int id) {

        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage());
        } finally {
           ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Task> getAll(int idProject) {
        String sql = "SELECT * FROM tasks  WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //lista de tarefas que sera devolvida quando a chamada do metodo acontecer
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();
            
            //Enquanto existir dados no banco de dados, fa�a
            while (resultSet.next()) {

                Task task = new Task();

                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                //Adiciono o contato recuperado, a lista de contatos
                tasks.add(task);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return tasks;
    }

    

}

