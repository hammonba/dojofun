(ns dojofun.core
    "ben hammond's fill buzz bazz solution")

(defn build-buzz-fn
  "takes a divisor and a buzz output
   returns a function that will
    - expect a two element vector as input
    - if the first element of that vector divides into div then conj the
    buzz value to the accumulator"
  [div buzz]
  (fn [[num & _ :as pair]]
    (if (zero? (mod num div))
      (update pair 1 conj buzz)
      pair)))

(defn fizz-buzz-baz
  "return fizz buzz bazz for specified number range"
  [n]
  (sequence
   (comp (map (fn [v] [v []]))
         (map (build-buzz-fn 3 "Fizz"))
         (map (build-buzz-fn 5 "Buzz"))
         (map (build-buzz-fn 7 "Bazz"))
         (map (fn [[num acc]]
                (if (empty? acc)
                  (str num)
                  (clojure.string/join acc)))))
   (range (inc n))))

(def fbb
  "here's one I made earlier ..."
  (fizz-buzz-baz 110))

(defn build-segment
  "build a segment that we can cycle"
  [n buzz]
  (conj (repeat (dec n) nil) buzz))

(defn fizz-buzz-bazz-cycle-based
  "Shamelessly inspired by the infinite sequence approach"
  [n]
  (map #(or %1 (str %2))
       (apply map
              (comp not-empty str)
              (map (comp cycle build-segment)
                   [3 5 7]
                   ["Fizz" "Buzz" "Bazz"]))
       (range (inc n))))

(def fbb-cycle
  "here's another one I made slightly later"
  (fizz-buzz-bazz-cycle-based 110))

;; hope they got the same answer...
;; (except for first element
(println "equals?=> " (= fbb fbb-cycle))