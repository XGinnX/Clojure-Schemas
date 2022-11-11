(ns hospital_sales.aula1
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(defn adiciona-paciente
  [pacientes, paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

(defn adiciona-visita
  [visitas, paciente, novas-visitas]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(defn imprime-relatorio-de-paciente
      [visitas, paciente]
  (println "Visitas do paciente" paciente "são" (get visitas paciente))
  )

; Retorna um vetor com 3 pacientes
(defn testa-uso-de-pacientes []
  (let [jean {:id 26, :nome "Jean"}
        marina {:id 02, :nome "Marina"}
        renata {:id 19, :nome "Renata"}]
    (pprint [jean marina renata])))

; Retorna um mapa com os pacientes
(defn testa-uso-de-pacientes []
  (let [jean {:id 26, :nome "Jean"}
        marina {:id 02, :nome "Marina"}
        renata {:id 19, :nome "Renata"}
        ;Adiciona em pacientes o retorno da função "adiciona-paciente"
        ;Toda vez que adiciona o paciente o reduce cria uma lista para que seja adicionado o outro
        pacientes (reduce adiciona-paciente {} [jean, marina, renata])]
    (pprint pacientes)))

(defn testa-uso-de-pacientes []
  (let [jean {:id 26, :nome "Jean"}
        marina {:id 02, :nome "Marina"}
        renata {:id 19, :nome "Renata"}
        pacientes (reduce adiciona-paciente {} [jean, marina, renata])
        visitas {}
        visitas (adiciona-visita visitas 26 ["01/01/2022"])
        visitas (adiciona-visita visitas 02 ["01/02/2022","07/02/2022"])
        visitas (adiciona-visita visitas 19 ["01/03/2022"])]
    (pprint pacientes)
    (pprint visitas)
    (imprime-relatorio-de-paciente visitas 26)))

(testa-uso-de-pacientes)

;SCHEMA serve como validador.
; S/valide = S de schema
(pprint (s/validate Long 15))

; Define que toda s/fn vai ser validado
(s/set-fn-validation! true)

(s/defn teste-simples [x]
  (pprint x))
(teste-simples "Jean")

(s/defn teste-validacao-simples [x :- Long]
  (pprint x))

(teste-validacao-simples 02)

(s/defn imprime-relatorio-de-paciente
  [visitas, paciente :- Long]
  (println "Visitas do paciente" paciente "são" (get visitas paciente))
  )
(testa-uso-de-pacientes)

(s/defn novo-paciente
  [id :- Long, nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 19 "Renata"))

;Validaçaão de schemas é principalmente usando quando:
; 1. Estão sendo realizados testes automatizados
; 2. Na borda do sistema, quando recebe info de terceiros.

;Schemas podem nos ajudar em diversas situações. Qual cenário faz sentido para mantermos nossas validações de schemas?
;Ativo em desenvolvimento e testes com boa qualidade, desativado em produção exceto nas camadas de entrada de dados externos.
;Essa abordagem faz sentido pois temos as garantias dos schemas nas bordas dos sistemas em produção e dentro do sistema em testes com qualidade.