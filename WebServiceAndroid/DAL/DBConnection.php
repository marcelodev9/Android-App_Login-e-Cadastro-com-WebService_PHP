<?php


include_once 'Config.php';

/** Essa classe sera responsavel pela conexão com a base de dados 
    Sera utilizado a classe PDO, cujo qual sera responsavel pela conexão 
 *  **/
 final class DBConnection {
     
    /**** Declarando os atributos da classe ****/

    //Variaveis de classe 
    private static $pdo;
    
    //Método construtor da classe definido como privado para impedir essa classe seja instanciada
    private function __construct() {
        
    }

     /* 
     * @return false ou objeto PDO
     */
    //Método de classe que cria a conexão com o banco de dados (retornando um objeto pdo ou false para erros)
    private static function criarConexao() {
        try {
            self::$pdo = new PDO("mysql:host=" . HOST . "; dbname=" . DBNAME . ";", USER, PASSWORD, array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8"));
            self::$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            return self::$pdo;
        } catch (PDOException $e) {
            //echo "Erro ao instanciar a conexão com a base de dados \nDescrição: " . $e;
            return false;
        }
    }
     
    /* 
     * @return método criar conexão
     */
    //Método de classe que retornara uma instância nova ou já existente do PDO (retorna objeto pdo ou false)
    public static function gerarInstancia() {
        //Se o objeto já tiver sido instânciado anteriormente e a variavel static conter sua referencia, retorna sua referencia
        if (isset( self::$pdo)) {
            return self::$pdo; //retornando instancia já criada
        }
        //Instancia um novo objeto pdo e o retorna
        else {            
            return self::criarConexao(); //retornando umva nova instancia ou false (erro)
        }
    }

}
