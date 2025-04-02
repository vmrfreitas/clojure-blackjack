(ns clojure-blackjack.game
  (:require [card-ascii-art.core :as card]))

(defn new-card []
  "Generates a card number between 1 and 13"
  (inc (rand-int 13)))

(defn player [player-name]
  (let [card-1 (new-card)
        card-2 (new-card)]
    {:player-name player-name
     :cards       [card-1 card-2]}))

(card/print-player (player "Milas"))
(card/print-player (player "Dealer"))
