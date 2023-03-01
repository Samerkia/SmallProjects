using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PortalTrap : Interact
{
    
    void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.CompareTag("Player"))
        {
            inventory.setHealth((inventory.getHealth() - 50f));
        }
    }
}