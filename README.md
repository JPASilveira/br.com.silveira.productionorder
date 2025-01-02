
**Sistema de Geração de Ordens de Produção**

**Introdução**

Este projeto tem como objetivo otimizar os processos de produção, oferecendo uma ferramenta intuitiva para a geração de ordens de produção. Desenvolvido em Java com a biblioteca Swing, o sistema permite o cadastro e gestão de produtos, clientes e a geração de ordens de produção de forma eficiente.

**Tecnologias Utilizadas**

* **Linguagem:** Java
* **Framework:** Swing
* **Banco de Dados:** H2
* **IDE:** IntelliJ IDEA + Swing UI Design Plugin
* **Controle de Versão:** Git

**Estrutura do Projeto**

O projeto está organizado em pacotes distintos para facilitar a manutenção e a compreensão do código:

* **model:** Representa as entidades do sistema (produtos, clientes, ordens de produção, etc.) como classes Java.
* **view:** Contém as classes responsáveis pela interface gráfica do usuário, utilizando componentes Swing.
* **controller:** Atua como intermediário entre a view e o model, gerenciando as ações do usuário e atualizando a interface.
* **repository:** Realiza a persistência dos dados no banco de dados H2, utilizando JDBC.
* **util:** Contém classes auxiliares com funcionalidades comuns, como validação de dados, etc.
* **service:** Utilizado para encapsular a lógica de negócio mais complexa, separando-a da camada de controller.

**Banco de Dados**

O sistema utiliza um banco de dados H2 para armazenar as informações. As principais tabelas são:

* **registration:** Cadastro de clientes e fornecedores.
* **product:** Cadastro de produtos, incluindo informações sobre grupos, unidades de medida e composição.
* **production_order:** Armazena as ordens de produção geradas, com detalhes sobre o produto, quantidade, status, etc.
* **product_composition:** Define a composição de produtos complexos a partir de produtos mais simples.

**Instalação e Execução**

   **Pré-requisitos:**
   * JDK 21 ou superior instalado.
   * Ferramenta para gerenciamento de pacotes (Maven ou Gradle).

**Instaladores**

- [Instalador para Ubuntu](https://github.com/JPASilveira/br.com.silveira.productionorder/releases/download/v.1.01b/Ubuntu.Install.zip)
- [Instalador para Windows](https://github.com/JPASilveira/br.com.silveira.productionorder/releases/download/v.1.01b/Windows.Install.zip)


**Licença**

 Production Order © 2024 by JPASilveira is licensed under Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
