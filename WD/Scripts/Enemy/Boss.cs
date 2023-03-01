using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;
using UnityEngine.UI;


public class Boss : Interact
{
    public GameObject Player;
    public float dist;

    public bool isAngry;

    public NavMeshAgent work;

    private float BOSS_MAX_health = 250f;
    public float BOSS_Current_health = 250f;
    public Image bar;
    public GameObject Container;

    void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.CompareTag("Player"))
        {
            inventory.setHealth((inventory.getHealth() - 30f));
        }
    }

    public override void OnInteract()
    {
        BOSS_Current_health -= 20;

        if (BOSS_Current_health <= 0)
        {
            gameObject.SetActive(false);
            Destroy(Container);
        }
    }

    float getBossHealth()
    {
        return BOSS_Current_health;
    }

    void Update()
    {
        dist = Vector3.Distance(Player.transform.position, this.transform.position);

        if(dist <= 15)
        {
            isAngry = true;
            Container.SetActive(true);
        }
        if(dist >= 15)
        {
            isAngry = false;
        }
        if(isAngry)
        {
            work.isStopped = false;
            work.SetDestination(Player.transform.position);
        }
        if(!isAngry)
        {
            work.isStopped = true;
        }

        bar.fillAmount = getBossHealth() / BOSS_MAX_health;
    }

}
