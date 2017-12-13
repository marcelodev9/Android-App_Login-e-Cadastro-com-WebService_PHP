<?php

include_once './Entidade/Usuario.php';
include_once 'DBConnection.php';
include_once 'IUsuario.php';

//Classe final para impedir que a mesma seja herdada
final class UsuarioDAL implements IUsuario {

    protected $cmdSelects = "";

    /* Método construtor publico, classe permite novas intancias */

    public function __construct() {
        
    }

    public function insert(\Usuario $usuario) {
        $this->con = DBConnection::gerarInstancia();
        if ($this->con) {
            $cmd = "INSERT INTO tb_usuario (_Nome, _Cel, _Senha) VALUES (?, ?, ?)";
            $stm = $this->con->prepare($cmd);
            $stm->bindValue(1, $usuario->getNome());
            $stm->bindValue(2, $usuario->getCelular());
            $stm->bindValue(3, $usuario->getSenha());
            try {
                $stm->execute();
                if ($stm->rowCount() > 0) {
                    return SUCESSO;
                } else {
                    return SEM_REGISTROS;
                }
            } catch (PDOException $e) {
                return ERRO_INSTRUCAO;
            }
        } else {
            return ERRO_DB;
        }
    }

    public function select($id) {
        $this->con = DBConnection::gerarInstancia();
        if ($this->con) {
            $cmd = "SELECT * FROM tb_usuario WHERE _Id = (?)";
            $stm = $this->con->prepare($cmd);
            $stm->bindValue(1, $usuario->$id);
            try {
                $stm->execute();
                if ($stm->rowCount() > 0) {
                    return $stm->fetch(PDO::FETCH_ASSOC);
                } else {
                    return SEM_REGISTROS;
                }
            } catch (PDOException $e) {
                return ERRO_INSTRUCAO;
            }
        } else {
            return ERRO_DB;
        }
    }
    
    
    public function login(Usuario $u) {
        $this->con = DBConnection::gerarInstancia();
        if ($this->con) {
            $cmd = "SELECT * FROM tb_usuario WHERE _Cel = (?) AND _Senha = (?)";
            $stm = $this->con->prepare($cmd);
            $stm->bindValue(1, $u->getCelular());
            $stm->bindValue(2, $u->getSenha());
            try {
                $stm->execute();
                if ($stm->rowCount() > 0) {
                    return $stm->fetch(PDO::FETCH_ASSOC);
                } else {
                    return SEM_REGISTROS;
                }
            } catch (PDOException $e) {
                return ERRO_INSTRUCAO;
            }
        } else {
            return ERRO_DB;
        }
    }
    
    

}
