(ns advent2015.day7
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def wire-defs (->>
                 (slurp (io/resource "../src/advent2015/day7.txt"))
                 str/split-lines))

(defn wire-to-def [wire-def]
  (->> (re-find #"(.*)->(.*)" wire-def)
       (drop 1)
       (map str/trim)
       ((fn [[a b]] {b a}))))

(def wires (apply merge (map wire-to-def wire-defs)))

(defmacro rc- [x] `(~'resolve-cmd ~x ~'network))
(defmacro op-recur [op] `(~op (rc- ~'a) (rc- ~'c)))

(declare resolve-cmd)
(defn resolve-cmd- [w-def network]
  (let [[a b c] (str/split w-def #" ")]
    (cond
      (= a "NOT") (bit-not (resolve-cmd b network))
      (= b "OR") (op-recur bit-or)
      (= b "LSHIFT") (op-recur bit-shift-left)
      (= b "RSHIFT") (op-recur bit-shift-right)
      (= b "AND") (op-recur bit-and)
      (get network a) (resolve-cmd (get network a) network)
      :else (Integer/parseInt a))))

(def resolve-cmd (memoize resolve-cmd-))

(def solution-1 (resolve-cmd "a" wires))

(def solution-2 (resolve-cmd "a" (assoc wires "b" (str solution-1))))
