(ns clojure-blackjack.game
  (:require [card-ascii-art.core :as card]))

(defn new-card []
  "Generates a card number between 1 and 13"
  (inc (rand-int 13)))

; rules:
; J, Q, K = 10
; A can be 11 or 1, what's better for the player
(defn jqk->10 [card]
  (if (> card 10) 10 card))

(defn a->11 [card]
  (if (= card 1) 11 card))

(defn card-points [cards]
  (let [cards-no-jqk (map jqk->10 cards)
        cards-with-a11 (map a->11 cards)
        points-with-a11 (reduce + cards-with-a11)
        points-with-a1 (reduce + cards-no-jqk)]
    (if (> points-with-a11 21) points-with-a1 points-with-a11)))

(card-points [1 1 9])

(defn player [player-name]
  (let [card-1 (new-card)
        card-2 (new-card)
        cards [card-1, card-2]
        points (card-points cards)]
    {:player-name player-name
     :cards       cards
     :points      points}))

(card/print-player (player "Milas"))
(card/print-player (player "Dealer"))
