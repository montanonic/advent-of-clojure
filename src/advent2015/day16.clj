(ns advent2015.day16
  (:require [clojure.java.io :as io]
            [clojure.set :as set]))

(def raw-data (slurp (io/resource "../src/advent2015/day16.txt")))

(def raw-ticker-tape-msg "children: 3\ncats: 7\nsamoyeds: 2\npomeranians: 3\nakitas: 0\nvizslas: 0\ngoldfish: 5\ntrees: 3\ncars: 2\nperfumes: 1")

(defn parse-props
  ([props] (parse-props props keyword #(Integer/parseInt %)))
  ([props parse-key parse-value]
   (->> (re-seq #"(\w+): (\d+)" props)
        (map (fn [[_ k v]] [(parse-key k) (parse-value v)]))
        (into {}))))

(def target-aunt-props (parse-props raw-ticker-tape-msg))

(def aunts
  (for [[_ aunt-num props :as x] (re-seq #"Sue (\d+)(.*)" raw-data)]
    (conj (parse-props props) [:aunt-num (Integer/parseInt aunt-num)])))

(defn solve-1 []
  (let [target-aunt-prop-set (set target-aunt-props)]
    (first (some
             #(if (set/subset? (set (dissoc % :aunt-num)) target-aunt-prop-set) %)
             aunts))))

(defn enough-cats-and-trees [props]
  (let [{:keys [:cats :trees]} props]
    (and (if cats (> cats (:cats target-aunt-props)) true)
         (if trees (> trees (:trees target-aunt-props)) true))))

(defn enough-pomeranians-and-goldfish [props]
  (let [{:keys [:pomeranians :goldfish]} props]
    (and (if pomeranians (< pomeranians (:pomeranians target-aunt-props)) true)
         (if goldfish (< goldfish (:goldfish target-aunt-props)) true))))

(defn solve-2 []
  (->> aunts
       (filter #(and (enough-cats-and-trees %) (enough-pomeranians-and-goldfish %)))
       (some #(if (set/subset?
                    (set (dissoc % :aunt-num :cats :trees :pomeranians :goldfish))
                    (set target-aunt-props)) %))
       :aunt-num))
