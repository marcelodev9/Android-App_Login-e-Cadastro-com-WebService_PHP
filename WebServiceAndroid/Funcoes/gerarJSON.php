<?php


function gerarArrayJson($status, $msg, $dados){
    $array = array("status_requisicao"=> $status, "msg" => $msg, "retorno" => $dados);
    return json_encode($array);
}
