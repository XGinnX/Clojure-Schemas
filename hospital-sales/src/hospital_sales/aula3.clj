(ns hospital_sales.aula3
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

 (def PosInt (s/pred pos-int? 'inteiro-positivo))
(def Paciente
  {:id PosInt, :nome s/Str})

(s/defn novo-paciente :- Paciente
  [id :- PosInt,
   nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 02, "Marina"))
;(pprint (novo-paciente -3, "Marina"))

(defn Maior-ou-igual-a-zero? [x] (>= x 0))
(def ValorFinanceiro (s/constrained s/Num Maior-ou-igual-a-zero?))

(def Pedido
  {:paciente Paciente
   :valor ValorFinanceiro
   :procedimento s/Keyword})

(s/defn novo-pedido :- Pedido
  [paciente :- Paciente,
   valor :- ValorFinanceiro,
   procedimento :- s/Keyword]
  {
      :paciente     paciente,
      :valor        valor,
      :procedimento procedimento
   })
(pprint (novo-pedido (novo-paciente 02, "marina"), 300, :raio-x))
;(pprint (novo-pedido (novo-paciente 02, "marina"), -300, :raio-x))

(def Numeros [s/Num])

(pprint (s/validate Numeros [1]))
(pprint (s/validate Numeros [1, 2, 3, 4, 5 ]))
(pprint (s/validate Numeros [1, 1.5, 2, 2.5]))
(pprint (s/validate Numeros []))
(pprint (s/validate Numeros nil))e

(def Plano [s/Keyword])

(def Paciente
{:id PosInt, :nome s/Str, :plano Plano})

(pprint (s/validate Paciente {:id 19, :nome "Renata", :plano [:raio-x, :ultrassom]}))
(pprint (s/validate Paciente {:id 19, :nome "Renata", :plano [:raio-x]}))
(pprint (s/validate Paciente {:id 19, :nome "Renata", :plano []}))
(pprint (s/validate Paciente {:id 19, :nome "Renata", :plano nil}))


;Vetores com 0 ou mais elementos de um único tipo são tranquilos de modelar e lidar no dia a dia.
;Vetores que misturam tipos de acordo com posições são complicados por se assemelham a objetos ou mapas, mas mais restritos.
;Vetores por exemplo não permitem a inexistência de valores como um mapa permitiria ao representar o mesmo tipo de dado.