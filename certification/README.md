# Certification C3 — Juan Pablo Borrero Morales

## Cover

| Field | Value |
|---|---|
| Full name | Juan Pablo Borrero Morales |
| GitHub user | JUANDAX233 |
| Group | G2 |
| Team | BarberSaaS |
| Team -docs repository | https://github.com/code-corhuila/barber-saas-docs |
| Course | HCCDP-Solution Architectures Course |
| Course link | https://e.huawei.com/en/talent/ict-academy/#/ictcourses-detail?courseId=6WtKiPd1S1YelBB0AUbE1vXlPgk |
| Start date | 2026-10-28 |
| Completion date | 2026-11-13 |

## Study log

| Date | Hours | Chapters or labs | Evidence file |
|---|---|---|---|
| 2026-10-29 | 2 | Pre-class Guidance; Chapter 1 Architecture Design Methodology on Huawei Cloud | evidence/01-chapter-01.png |
| 2026-10-29 | 1 | Chapter 2 Typical Service Implementation Solutions on the Cloud | evidence/02-chapter-02.png |
| 2026-10-31 | 3 | Chapter 3 Cloud Compute Solution Design | evidence/03-chapter-03.png |
| 2026-10-31 | 3 | Chapter 4 Cloud Storage Solution Design | evidence/04-chapter-04.png |
| 2026-11-01 | 4 | Chapter 5 Cloud Network Solution Design | evidence/05-chapter-05.png |
| 2026-11-01 | 3 | Chapter 6 Cloud Database Solution Design | evidence/06-chapter-06.png |
| 2026-11-07 | 3 | Chapter 7 Cloud Security Solution Design | evidence/07-chapter-07.jpeg |
| 2026-11-08 | 3 | Chapter 8 Cloud O&M Solution Design | evidence/08-chapter-08.jpeg |
| 2026-11-08 | 4 | Chapter 9 Distributed Architecture Design | evidence/09-chapter-09.jpeg |
| 2026-11-08 | 4 | Chapter 10 Highly Scalable System Design | evidence/10-chapter-10.jpeg |
| 2026-11-08 | 3 | Chapter 11 Disaster Recovery System Design | evidence/11-chapter-11.jpeg |
| 2026-11-10 | 2 | Chapter 12 Automated Deployment | evidence/12-chapter-12.jpeg |
| 2026-11-10 | 3 | Chapter 13 Containers and Agility | evidence/13-chapter-13.png |
| 2026-11-11 | 3 | Chapter 14 Huawei Cloud Advanced Services | evidence/14-chapter-14.jpg |
| 2026-11-11 | 3 | Lab 1 Design Cases and Practices of an O&M Solution | evidence/15-lab-1.png |
| 2026-11-12 | 2 | Lab 2 High-Performance Network Solution | evidence/16-lab-2.jpg |
| 2026-11-12 | 2 | Lab 3 Highly Reliable Database Solution | evidence/17-lab-3.jpg |
| 2026-11-12 | 2 | Lab 4 Cloud Computing and Storage | evidence/18-lab-4.jpg |
| 2026-11-13 | 2 | Final Exam (HCCDP-Solution Architectures Course Mock Exam, 90 minutes) | evidence/19-final-exam.png |
| Total | 52 | | |

## Labs

| Evidence file | Lab name | What I built | What failed first and how I fixed it |
|---|---|---|---|
| evidence/15-lab-1.png | Design Cases and Practices of an O&M Solution | I connected an ECS to a Log Tank Service log group through ICAgent and set a Cloud Eye CPU alarm that notifies through SMN. | No logs reached LTS because the ingestion rule pointed to a log path that did not exist on the ECS; I corrected the path and the lines appeared. |
| evidence/16-lab-2.jpg | High-Performance Network Solution | I built two VPCs joined by VPC peering, and gave a private ECS internet access through a NAT Gateway SNAT rule with an EIP. | The ECS in one VPC could not ping the other because the peering had no routes; I added the peer CIDR to the route table of both VPCs. |
| evidence/17-lab-3.jpg | Highly Reliable Database Solution | I created an RDS primary/standby instance, connected to it from an ECS, ran a manual switchover and restored a backup to check that the data stayed consistent. | The ECS could not connect to RDS because the security group did not allow the database port; I added an inbound rule for the ECS subnet only. |
| evidence/18-lab-4.jpg | Cloud Computing and Storage | I deployed the Discuz! forum on an ECS with its database on RDS, made a private image of that ECS and launched a second server from the image. | The forum page did not load from the browser because port 80 was closed in the security group; I opened it and the installer ran. |

## Final exam

- Score: 85/100 (passing score 70)
- Date: 2026-11-13
- Evidence: evidence/19-final-exam.png
- Certificate: certificate.pdf (Certificate of Completion, code ICT20261114000017, issued 2026-11-14)

## Skills summary

In this course I learned to review a design against five principles: reliability, security, performance efficiency, cost and operations.
I can choose a disaster recovery pattern (backup and restore, active-standby or active-active) from the RTO and RPO a system needs.
I applied it to BarberSaaS, moving PostgreSQL to an RDS primary/standby pair with WAL archived to a second region.
I learned to scale stateless services horizontally with an autoscaling policy on a measured metric, keeping locks and sessions out of the instances.
In the labs I built VPC peering, NAT Gateway, RDS switchover and centralized logs with LTS and Cloud Eye alarms.
I also learned to automate deployment as a pipeline of build, test, image, deploy and rollback stages tied to release tags.
