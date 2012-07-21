(ns byr.utils)

(def alphabet "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn int->base62 
  "Converts an integer to a base62 number"
  ([n] (int->base62 (rem n 62) (quot n 62) ""))
  ([remainder number accum] 
     (cond
       (zero? number) (str (nth alphabet remainder) accum)
       :else (recur (rem number 62) (quot number 62) (str (nth alphabet remainder) accum)))))

(defn base62->int
  "Converts a base 62 string to number"
  ([str] (base62->int  (reverse str) 0 0))
  ([inverse-str power accum] 
     (cond
       (empty? inverse-str) accum
       :else (base62->int 
              (rest inverse-str) 
              (+ power 1) 
              (+ accum (* (bigint (Math/pow  62 power)) 
                          (.indexOf alphabet (str (first inverse-str)))))))))
