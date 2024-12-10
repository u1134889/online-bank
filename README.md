# online-bank
Kubernetes Online Bank practice project

## Purpose

This project is setup as a learning template for various technologies.
The foremost goal is to setup a framework that allows for easy local development for complex system using kubernetes.

Beyond that, it is desirable to create an online banking application that meets these requirements
- Customer Page
  - Registration of account.
  - Verification of account via email.
  - Forgot password via email. Login of verified account.
  - Displaying of 6 offers of the bank.
  - Account settings
    - Changing profile picture
    - Changing password
    - Changing account information
  - Viewing of accounts
  - Locking of savings accounts
  - Can view transactions history.
  - Can submit reports to the administrator.
  - Can transfer money to other accounts.
  - Can apply for loans/credit cards.
  - Can view points.
  - Can view interest.
- Admin Page
  - Admin login
  - Account settings
    - Changing profile picture
    - Changing password
    - Changing account information
    - Blocking and unblocking
  - Can do arching/multiple archiving of accounts.
  - Can perform CRUD.
    - Offers
    - Announcement
  - Create client accounts.
  - Can manage client accounts
  - Can view the highest investors/savings accounts
  - Can do walk-in transactions
    - Create client accounts.
    - Application for loans/credit cards
  - Can modify customer page.
    - Background
    - Color
    - Logo
    - slideshow
  - Can generate reports.
  - Can view payment history.
  - Pointing system/redeem

## Running

### Prerequisites

- install minikube
- install kubectl
- install opentofu
- install docker (and docker desktop depending on OS)

### Daily startups

- start docker engine (often by starting docker desktop)
- run `minikube start`
- run `minikube addons enable registry`
- in a shell that can stay open in the background, run `kubectl port-forward --namespace kube-system service/registry 5000:80`
- run `docker run --rm -it -d --network=host alpine ash -c "apk add socat && socat TCP-LISTEN:5000,reuseaddr,fork TCP:host.docker.internal:5000"`

### Iterating

- `mvn package`
- `docker push "localhost:5000/rogosienski/online-bank-backend:$(mvn help:evaluate "-Dexpression=project.version" -q -DforceStdout)"`
- `tofu -chdir=infrastructure/src/main/tofu plan -var="backend_version=$(mvn help:evaluate "-Dexpression=project.version" -q -DforceStdout)"`
- `tofu -chdir=infrastructure/src/main/tofu apply -var="backend_version=$(mvn help:evaluate "-Dexpression=project.version" -q -DforceStdout)"`