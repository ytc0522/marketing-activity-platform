

local stock = redis.call('hget', KEYS[1], ARGV[1])
if tonumber(stock) > tonumber(ARGV[2]) then
    return redis.call('hincrby', KEYS[1], ARGV[1], -tonumber(ARGV[2]))
else
    return false
end