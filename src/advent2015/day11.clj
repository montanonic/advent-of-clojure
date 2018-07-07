(ns advent2015.day11
  (:require [clojure.string :as str])
  (:use [advent-of-clojure.utils]))

(defn inc-char-by [n c]
  (let [a (int \a)
        offset (- (int c) a)] ; distance from 'a
    (-> (mod (+ n offset) 26)
        (+ a) ; turn back into valid char number
        char)))

(defn char-would-wrap? [n c]
  (> (+ n (int c)) (int \z)))

(def bad-letters (set "iol"))

(defn increasing-straight? [n s]
  {:pre (>= n 2)}
  (->> (map int s)
       (partition 2 1) ; Pairs
       (map (fn [[x y]] (= (inc x) y))) ; Check each pair for a run of 2
       (partition-by true?) ; Cluster answers
       (filter #(= (first %) true)) ; Remove 'false clusters
       (some #(= (count %) (dec n))))) ; Check if any cluster is long enough

(defn has-letter-pairs? [n s]
  (->> s
       (partition-by identity)
       (filter #(<= 2 (count %)))
       count
       (<= n)))

(defn valid-password [s]
  (and
    (increasing-straight? 3 s)
    (not-any? bad-letters s)
    (has-letter-pairs? 2 s)))

(defn inc-string [s]
  (letfn [(return [s] (apply str (reverse s)))]
    (loop [r (vec (reverse s)), n 0]
      (cond
        (= n (count s)) (return (cons \a r))
        (char-would-wrap? 1 (nth r n)) (recur (assoc r n \a) (inc n))
        :else (-> r (update n #(inc-char-by 1 %)) return)))))

(def input "cqjxjnds")

(defn solve [x]
  (->> x
       (iterate inc-string)
       (drop 1)
       (some #(if (valid-password %) %))))

(defn solution-1 [] (solve input))

(defn solution-2 [] (solve (solution-1)))