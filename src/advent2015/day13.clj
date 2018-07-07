;; Cheating here by using @derekslager's code.
;; Tough challenge for me, though I came into it wanting to not rely
;; on a brute-force solution, and after hitting wall after wall, didn't
;; have the enthusiasm to go back and brute-force.
(ns advent2015.day13
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [permutations]]))

(defn score-arrangement [stats arrangement]
  (let [diners (count arrangement)]
    (->> (for [[n person] (map-indexed vector arrangement)]
           (+ (or (get stats [person (get arrangement (mod (- n 1) diners))]) 0)
              (or (get stats [person (get arrangement (mod (+ n 1) diners))]) 0)))
         (reduce +))))

(defn parse-input [resource]
  (let [matches (re-seq #"(\w+) would (gain|lose) (\d+) happiness units by sitting next to (\w+)\." resource)]
    (into {} (for [[_ person direction amount next-to] matches]
               [[person next-to] (* (Integer/parseInt amount) (if (= "lose" direction) -1 1))]))))

(defn run []
  (let [resource (slurp (io/resource "../src/advent2015/day13.txt"))
        stats (parse-input resource)
        diners (set (map first (keys stats)))]

    ;; part 1
    (apply max
           (for [arrangement (permutations diners)]
             (score-arrangement stats (vec arrangement))))

    ;; part 2
    (let [new-diners (conj diners "Derek")]
      (apply max
             (for [arrangement (permutations new-diners)]
               (score-arrangement stats (vec arrangement)))))))


;; Original attempt:
;(ns advent2015.day13
;  (:require [clojure.java.io :as io]
;            [com.rpl.specter :as S]
;            [clojure.core.logic :as logic]
;            [clojure.core.logic.fd :as fd])
;  (:use [advent-of-clojure.utils]))
;
;(def raw-data (->>
;                (slurp (io/resource "../src/advent2015/day13.txt"))))
;
;(def diffs-and-people
;  (->>
;    raw-data
;    (re-seq #"(\w+).*(gain|lose).*\b(\d+).*\b(\w+)")
;    (reduce
;      (fn [[m s] [_ person-a gain-or-lose _amount person-b]]
;        (let [_amount (Integer/parseInt _amount)
;              amount (condp = gain-or-lose
;                       "gain" _amount
;                       "lose" (- _amount))]
;          [(update m #{person-a person-b} (fnil + 0) amount)
;           (conj s person-a person-b)]))
;      [{} #{}])))
;
;(def diffs (first diffs-and-people))
;(def people (second diffs-and-people))
;
;(defn calc-happiness [diffs]
;  (reduce
;     (fn [[sum people-counts :as a] [pair amount]]
;       (let [all-counted-twice (not (S/selected-any? [S/MAP-VALS #(not= 2 %)] people-counts))
;             not-counted-twice (fn [c] (every? #(> 2 (get people-counts %)) c))]
;         (cond
;           all-counted-twice (reduced sum)
;           (not-counted-twice pair) [(+ sum amount) (reduce #(update %1 %2 inc) people-counts pair)]
;           :else a)))
;     [0 (zipmap people (repeat 0))] diffs))
