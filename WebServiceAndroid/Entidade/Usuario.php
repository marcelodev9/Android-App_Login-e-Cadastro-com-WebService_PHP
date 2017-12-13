<?php


class Usuario {
 
    private $nome;
    private $id;
    private $celular;
    private $senha;
    
    
    //Método construtor da classe
    public function __construct() {        
    }
    
    public function setNome($nome){
        $this->nome = $nome;
    }
    
    public function getNome(){
        return $this->nome;
    }
    
    public function setId($id){
        $this->id = $id;
    }
    
    public function getId(){
        return $this->id;
    }
    
    public function setCelular($celular){
        $this->celular = $celular;
    }
    
    public function getCelular(){
        return $this->celular;
    }
    
    public function setSenha($senha){
        $this->senha = $senha;
    }
    
    public function getSenha(){
        return $this->senha;
    }
     
}