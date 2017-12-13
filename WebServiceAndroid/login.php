<?php

include_once './BLL/UsuarioBLL.php';
include_once './Entidade/Usuario.php';
include_once './Funcoes/gerarJSON.php';
include_once './DAL/Config.php';

if(isset($_POST['senha']) && isset($_POST['celular'])){
    $usuario = new Usuario();
    $usuario->setCelular($_POST['celular']);
    $usuario->setSenha($_POST['senha']);
    $usuarioBLL = new UsuarioBLL();
    echo $usuarioBLL->logarUsuario($usuario);
}
else{
    echo gerarArrayJson(PARAMETROS_INVALIDOS, "Parâmetros passados invalidos!", null);
}