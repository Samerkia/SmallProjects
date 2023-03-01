using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spikes : Interact
{
    void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.CompareTag("Player"))
        {
            inventory.setHealth((inventory.getHealth() - 15f));
        }
    }
}
