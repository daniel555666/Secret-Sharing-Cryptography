# Secret Sharing - Cryptography <br />

## Implementation of secret sharing (subject in cryptography) in 2 ways:
###  CNF - Secret Sharing <br /> Shamir - Secret Sharing

CNF Secret Sharing involves dividing a secret into multiple shares, such that a certain number of shares are required to reconstruct the secret. This method is based on polynomial interpolation and uses Chinese Remainder Theorem (CRT) for efficient reconstruction.

Shamir Secret Sharing, named after its inventor Adi Shamir, is a method for distributing a secret among a group of participants in such a way that any subset of a certain size can reconstruct the secret, but smaller subsets cannot. This method is based on polynomial evaluation and uses finite field arithmetic for reconstruction.

Both CNF Secret Sharing and Shamir Secret Sharing are widely used in various applications, including secure multiparty computation, secure distributed storage, and secure communication.