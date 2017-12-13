<?php

include_once './DAL/UsuarioDAL.php';
include_once './Funcoes/gerarJSON.php';

/**
 * Essa classe devera ter modificador final para impedir que a mesma seja herdada
 */
final class UsuarioBLL {

    private $result = null;
    private $msg = null;
    private $dados = null;
    private $usuarioDAL = null;
    
    public function __construct() {
        $this->usuarioDAL = new UsuarioDAL();
    }

    /*
     * @param objeto usuario com os dados fornecidos pelo usuario para cadastro no sistema
     * @return uma array json contendo informações cruciais para uso na client side
     */

    public function cadastrarUsuario(Usuario $usuario) {
        $this->result = $this->usuarioDAL->insert($usuario);

        if ($this->result === SUCESSO) {
            $this->msg = "Cadastrado realizado com sucesso!";
        } else if ($this->result === ERRO_INSTRUCAO) {
            $this->msg = "Usuario já cadastrado!";
        } else if ($this->result === ERRO_DB) {
            $this->msg = "Erro no banco de dados!";
        }
        return gerarArrayJson($this->result, $this->msg, $this->dados);
    }

    public function logarUsuario(Usuario $u) {
        $this->result = $this->usuarioDAL->login($u);
        if ($this->result === SEM_REGISTROS) {
            $this->msg = "Usuario ou senha invalidos!";
            $this->status = SEM_REGISTROS;
        } else if ($this->result === ERRO_INSTRUCAO) {
            $this->msg = "Erro na execução da instrução da base de dados!";
            $this->status = ERRO_INSTRUCAO;
        } else if ($this->result === ERRO_DB) {
            $this->msg = "Erro no banco de dados!";
            $this->status = ERRO_DB;
        } else {
            $this->msg = "Login realizado com sucesso!";
            $this->status = SUCESSO;
            $this->dados = $this->result;
        }
        return gerarArrayJson($this->status, $this->msg, $this->dados);
    }
  
}
