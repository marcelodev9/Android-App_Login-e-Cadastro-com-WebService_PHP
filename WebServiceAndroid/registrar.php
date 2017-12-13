<?php

include_once './BLL/UsuarioBLL.php';
include_once './Entidade/Usuario.php';
include_once './Funcoes/gerarJSON.php';
include_once './DAL/Config.php';

if(isset($_POST['nome']) && isset($_POST['celular']) && isset($_POST['senha'])){
    $usuarioBLL = new UsuarioBLL();
    $usuario = new Usuario();
    $usuario->setNome($_POST['nome']);
    $usuario->setCelular($_POST['celular']);
    $usuario->setSenha($_POST['senha']);    
    
    echo $usuarioBLL->cadastrarUsuario($usuario);
}

else{
    echo gerarArrayJson(PARAMETROS_INVALIDOS, "Parâmetros passados invalidos!", null);
}


