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

(defn new-player [player-name]
  (let [card-1 (new-card)
        card-2 (new-card)
        cards [card-1, card-2]
        points (card-points cards)]
    {:player-name player-name
     :cards       cards
     :points      points}))

(defn more-card [player]
  (let [card (new-card)
        cards (conj (:cards player) card)
        new-player (update player :cards conj card)
        points (card-points cards)]
    (assoc new-player :points points)))

(defn player-decision-continue [player]
  (= (read-line) "yes"))

(defn dealer-decision-continue [player-points dealer]
  (let [dealer-points (:points dealer)]
    (<= dealer-points player-points)))

(defn game [player decision-fn]
  (println (:player-name player) ": one more card?")
  (if (decision-fn player)
    (let [player-with-more-cards (more-card player)]
      (card/print-player player-with-more-cards)
      (game player-with-more-cards decision-fn))
    player))

(def player (new-player "Milas"))
(card/print-player player)

(def dealer (new-player "Dealer"))
(card/print-player dealer)

(def player-after-game (game player player-decision-continue))
(game dealer (partial dealer-decision-continue (:points player-after-game)))
