module builder

go 1.25.7

require (
	github.com/xtls/xray-core v1.260123.1-0.20260206094241-12ee51e4bb1d
	golang.org/x/mobile v0.0.0-20260204172633-1dceadbbeea3
)

// grpc fix, should remove in next updates
replace google.golang.org/grpc v1.78.0 => google.golang.org/grpc v1.79.3

// grpc fix

replace github.com/2dust/AndroidLibXrayLite => ../../../../../build/v2ray/src
